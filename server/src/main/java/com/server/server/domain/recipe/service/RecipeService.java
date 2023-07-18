package com.server.server.domain.recipe.service;

import com.server.server.domain.ingredient.entity.Ingredient;
import com.server.server.domain.ingredient.service.IngredientService;
import com.server.server.domain.recommend.service.RecommendService;
import com.server.server.domain.user.entity.User;
import com.server.server.domain.user.service.UserService;
import com.server.server.domain.recipe.dto.RecipeDto;
import com.server.server.domain.recipe.entity.Recipe;
import com.server.server.domain.recipe.repository.RecipeRepository;
import com.server.server.domain.recommend.entity.Recommend;
import com.server.server.domain.recommend.repository.RecommendRepository;
import com.server.server.global.exception.BusinessLogicException;
import com.server.server.global.exception.ExceptionCode;
import com.server.server.global.s3.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RecipeService {
    @Autowired
    private final RecipeRepository recipeRepository;
    private final UserService userService;
    private final RecommendRepository recommendRepository;
    private final RecommendService recommendService;
    private final IngredientService ingredientService;
    private final S3Uploader s3Uploader;
    @PersistenceContext
    private final EntityManager entityManager;



    public Recipe createRecipe(Recipe recipe, MultipartFile recipeImage, List<MultipartFile> cookStepImage, long userId) {
        User user = userService.findUser(userId);
        List<Ingredient> ingredients = ingredientService.saveAll(recipe.getIngredients());
        for (Ingredient ingredient : recipe.getIngredients()) {
            ingredient.setRecipe(recipe);
        }
        recipe.setIngredients(ingredients);
        user.addRecipe(recipe);
        uploadImage(recipe, recipeImage, cookStepImage);
        return recipeRepository.save(recipe);
    }

    public void uploadImage(Recipe recipe, MultipartFile recipeImage, List<MultipartFile> cookStepImage) {
        String fileUrl = s3Uploader.upload(recipeImage);
        recipe.setRecipeImage(fileUrl);
        for (MultipartFile file : cookStepImage) {
            String url = s3Uploader.upload(file);
            recipe.addCookStepImage(url);
        }
    }

    public Recipe updateRecipe(Recipe recipe, MultipartFile recipeImage, List<MultipartFile> cookStepImage) {
        Recipe findRecipe = findRecipe(recipe.getRecipeId());

        Optional.ofNullable(recipe.getRecipeName())
                .ifPresent(name -> findRecipe.setRecipeName(name));
        Optional.ofNullable(recipe.getRecipeIntro())
                .ifPresent(intro -> findRecipe.setRecipeIntro(intro));
        if (recipe.getIngredients() != null) {
            findRecipe.setIngredients(recipe.getIngredients());
        }
        if (recipe.getCookStepContent().size() != 0) {
            findRecipe.setCookStepContent(recipe.getCookStepContent());
        }
        if (recipeImage != null) {
            String url = findRecipe.getRecipeImage();
            s3Uploader.delete(url);
            String fileUrl = s3Uploader.upload(recipeImage);
            findRecipe.setRecipeImage(fileUrl);
        }
        if (cookStepImage != null) {
            for (String url : findRecipe.getCookStepImage()) {
                s3Uploader.delete(url);
            }
            findRecipe.setCookStepImage(new ArrayList<>());
            for (MultipartFile file : cookStepImage) {
                String url = s3Uploader.upload(file);
                findRecipe.addCookStepImage(url);
            }
        }

        return recipeRepository.save(findRecipe);
    }

    public Recipe incrementViewCount(Recipe recipe) {
        recipe.setViews(recipe.getViews()+1);
        recipeRepository.save(recipe);
        return recipe;
    }
    public RecipeDto.RecommendResponse toggleRecipeRecommend(long userId, long recipeId) {
        User user = userService.findUser(userId);
        Recipe recipe = findRecipe(recipeId);
        List<Recommend> recommends = recipe.getRecommendList();

        Optional<Recommend> optionalRecommend = recommends.stream()
                .filter(recommend -> recommend.getUser().getUserId().equals(userId))
                .findFirst();
        RecipeDto.RecommendResponse response = new RecipeDto.RecommendResponse();
        response.setUserId(userId);
        response.setRecipeId(recipeId);

        if (optionalRecommend.isPresent()) {
            Recommend recommend = optionalRecommend.get();

            recommendService.deleteRecommend(user, recipe, userId, recipeId);

            recommendRepository.delete(recommend);

            response.setMessage("좋아요가 취소되었습니다.");
        } else {
            Recommend recommend = new Recommend(user, recipe);
            recipe.addRecommend(recommend);
            user.addRecommend(recommend);
            Recommend savedRecommend = recommendRepository.save(recommend);

            response.setRecommendId(savedRecommend.getRecommendId());
            response.setMessage("좋아요가 생성되었습니다.");
        }

        return response;
    }


    public void deleteRecipe(long recipeId) {
        Recipe findRecipe = findRecipe(recipeId);
        String url = findRecipe.getRecipeImage();
        s3Uploader.delete(url);

        for (String fileUrl : findRecipe.getCookStepImage()) {
            s3Uploader.delete(fileUrl);
        }

        recipeRepository.delete(findRecipe);
    }
    public Recipe findRecipe(long recipeId) {
        return findVerifiedRecipe(recipeId);
    }

    public Recipe findVerifiedRecipe(long recipeId) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);
        return optionalRecipe.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.RECIPE_NOT_FOUND));
    }

    // 레시피 제목으로 검색
    public Page<Recipe> searchRecipesByName(String recipeName, Pageable pageable) {
        return recipeRepository.findByRecipeNameContainingIgnoreCase(recipeName, pageable);
    }

    // 장바구니 속 재료로 검색
    public Page<Recipe> searchAllRecipesByIngredients(List<String> ingredients, Pageable pageable) {
        String countQuery = "SELECT COUNT(DISTINCT r) FROM Recipe r JOIN r.ingredients i WHERE i.ingredientName IN :ingredients " +
                "GROUP BY r " +
                "HAVING COUNT(DISTINCT i.ingredientName) = :ingredientCount " +
                "AND COUNT(DISTINCT i.ingredientName) = :ingredientListCount";
        TypedQuery<Long> countTypedQuery = entityManager.createQuery(countQuery, Long.class);
        countTypedQuery.setParameter("ingredients", ingredients);
        countTypedQuery.setParameter("ingredientCount", Long.valueOf(ingredients.size()));
        countTypedQuery.setParameter("ingredientListCount", (long) ingredients.size());
        Long totalCount = countTypedQuery.getSingleResult();

        String query = "SELECT r FROM Recipe r JOIN r.ingredients i WHERE i.ingredientName IN :ingredients " +
                "GROUP BY r " +
                "HAVING COUNT(DISTINCT i.ingredientName) = :ingredientCount " +
                "AND COUNT(DISTINCT i.ingredientName) = :ingredientListCount";
        TypedQuery<Recipe> typedQuery = entityManager.createQuery(query, Recipe.class);
        typedQuery.setParameter("ingredients", ingredients);
        typedQuery.setParameter("ingredientCount", Long.valueOf(ingredients.size()));
        typedQuery.setParameter("ingredientListCount", (long) ingredients.size());
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());
        List<Recipe> resultList = typedQuery.getResultList();

        return new PageImpl<>(resultList, pageable, totalCount);
    }


    //냉장고 속 재료로 검색
    public Page<Recipe> searchRecipesByIngredients(List<String> ingredients, Pageable pageable) {
        String countQuery = "SELECT COUNT(DISTINCT r) FROM Recipe r JOIN r.ingredients i WHERE i.ingredientName IN :ingredients";
        TypedQuery<Long> countTypedQuery = entityManager.createQuery(countQuery, Long.class);
        countTypedQuery.setParameter("ingredients", ingredients);
        Long totalCount = countTypedQuery.getSingleResult();

        String query = "SELECT DISTINCT r FROM Recipe r JOIN r.ingredients i WHERE i.ingredientName IN :ingredients";
        TypedQuery<Recipe> typedQuery = entityManager.createQuery(query, Recipe.class);
        typedQuery.setParameter("ingredients", ingredients);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());
        List<Recipe> resultList = typedQuery.getResultList();

        return new PageImpl<>(resultList, pageable, totalCount);
    }

    //전체 레시피 조회(하단 바)
    public Page<Recipe> getAllRecipes(Pageable pageable) {
        return recipeRepository.findAll(pageable);
    }

}
