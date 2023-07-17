package com.server.server.domain.recipe.mapper;

import com.server.server.domain.comment.dto.CommentDto;
import com.server.server.domain.comment.entity.Comment;
import com.server.server.domain.comment.mapper.CommentMapper;
import com.server.server.domain.ingredient.dto.IngredientDto;
import com.server.server.domain.ingredient.entity.Ingredient;
import com.server.server.domain.recipe.dto.RecipeDto;
import com.server.server.domain.recipe.entity.Recipe;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RecipeMapper {
    default Recipe postToRecipe(RecipeDto.Post post, List<Ingredient> ingredients){
        Recipe recipe = new Recipe();
        recipe.setRecipeName(post.getRecipeName());
        recipe.setRecipeIntro(post.getRecipeIntro());
        if ( recipe.getCookStepContent() != null ) {
            List<String> list = post.getCookStepContent();
            if ( list != null ) {
                recipe.getCookStepContent().addAll( list );
            }
        }
        recipe.setIngredients(ingredients);
        return recipe;
    }
    default Recipe patchToRecipe(RecipeDto.Patch patch, List<Ingredient> ingredients) {
        Recipe recipe = new Recipe();
        recipe.setRecipeId(patch.getRecipeId());
        recipe.setRecipeName(patch.getRecipeName());
        recipe.setRecipeIntro(patch.getRecipeIntro());
        if ( recipe.getCookStepContent().size() == 0 ) {
            List<String> list = patch.getCookStepContent();
            if ( list != null ) {
                recipe.getCookStepContent().addAll( list );
            }
        }
        recipe.setIngredients(ingredients);
        return recipe;
    }
    default RecipeDto.Response recipeToResponse(Recipe recipe) {
        if ( recipe == null ) {
            return null;
        }

        RecipeDto.Response response = new RecipeDto.Response();

        response.setRecipeId( recipe.getRecipeId() );
        response.setRecipeName( recipe.getRecipeName() );
        response.setRecipeImage( recipe.getRecipeImage() );
        response.setRecipeIntro( recipe.getRecipeIntro() );
        List<String> list = recipe.getCookStepContent();
        if ( list != null ) {
            response.setCookStepContent( new ArrayList<String>( list ) );
        }
        List<String> list1 = recipe.getCookStepImage();
        if ( list1 != null ) {
            response.setCookStepImage( new ArrayList<String>( list1 ) );
        }
        response.setViews( recipe.getViews() );
        response.setRecommendCount( recipe.getRecommendCount() );
        List<CommentDto.Response> responseList = new ArrayList<>();
        for (Comment comment : recipe.getCommentList()) {
            CommentDto.Response commentResponse = new CommentDto.Response();
            commentResponse.setCommentId(comment.getCommentId());
            commentResponse.setRecipeId(comment.getRecipe().getRecipeId());
            commentResponse.setUserId(comment.getUser().getUserId());
            commentResponse.setCommentContent(comment.getCommentContent());
            commentResponse.setCreatedAt(comment.getCreatedAt());
            commentResponse.setModifiedAt(comment.getModifiedAt());
            responseList.add(commentResponse);
        }
        response.setComments(responseList);
        List<IngredientDto.Response> ingredientResponse = new ArrayList<>();
        for (Ingredient ingredient : recipe.getIngredients()) {
            IngredientDto.Response ingreResponse = new IngredientDto.Response();
            ingreResponse.setIngredientId( ingredient.getIngredientId() );
            ingreResponse.setIngredientName( ingredient.getIngredientName() );
            ingreResponse.setQuantity( ingredient.getQuantity() );
            ingredientResponse.add(ingreResponse);
        }
        response.setIngredients(ingredientResponse);

        return response;
    }
    default List<RecipeDto.ListResponse> recipeToResponseList(List<Recipe> recipes){
        List<RecipeDto.ListResponse> list = new ArrayList<>();
        for (Recipe recipe : recipes) {
            RecipeDto.ListResponse response = new RecipeDto.ListResponse();
            response.setRecipeId(recipe.getRecipeId());
            response.setRecipeName(recipe.getRecipeName());
            response.setRecipeImage(recipe.getRecipeImage());
            response.setViews(recipe.getViews());
            response.setRecommendCount(recipe.getRecommendCount());
        }
        return list;
    }
    RecipeDto.PostResponse recipeToPostResponse(Recipe recipe);

    default List<RecipeDto.ListResponse> recipesToResponseList(List<Recipe> recipes) {
        return recipes.stream()
                .map(this::recipeToListResponse)
                .collect(Collectors.toList());
    }


    RecipeDto.ListResponse recipeToListResponse(Recipe recipe);

}
