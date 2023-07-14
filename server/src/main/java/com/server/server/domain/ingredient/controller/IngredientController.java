package com.server.server.domain.ingredient.controller;

import com.server.server.domain.ingredient.dto.IngredientDto;
import com.server.server.domain.ingredient.dto.IngredientResponseDto;
import com.server.server.domain.ingredient.entity.Ingredient;
import com.server.server.domain.ingredient.mapper.IngredientMapper;
import com.server.server.domain.ingredient.service.IngredientService;
import com.server.server.global.response.MultiResponseDto;
import com.server.server.global.response.SingleResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/ingres")
@Validated
@RequiredArgsConstructor
public class IngredientController {

private final IngredientService ingredientService;
private final IngredientMapper mapper;



    @PostMapping("/add")
    public ResponseEntity postIngredient(@RequestBody IngredientDto.Post requestBody) {    //재료 등록(유저의 재료목록에 추가)
       Ingredient ingredient = mapper.ingredientPostDtoToIngredient(requestBody);
       Ingredient saveIngredient = ingredientService.createIngredient(ingredient);

        return new ResponseEntity<>(new SingleResponseDto(mapper.ingredientResponseDtoToIngredient(saveIngredient)), HttpStatus.CREATED);
    }

    @PostMapping("/request")
    public ResponseEntity requestIngredient() {    //재료 추가 요청(마이페이지)
        return null;
    }

    @GetMapping
    public ResponseEntity<List<IngredientResponseDto>> getIngredients() {
        List<IngredientResponseDto> ingredients = ingredientService.findIngredients();
        return  ResponseEntity.ok(ingredients);
    }

    @DeleteMapping("/delete/{ingre-id}") //해당 재료 삭제
    public ResponseEntity deleteIngredient(@PathVariable("ingre-id") long ingredientId) {//재료 삭제
        ingredientService.deleteIngredient(ingredientId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
