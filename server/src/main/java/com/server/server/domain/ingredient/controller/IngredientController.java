package com.server.server.domain.ingredient.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ingres")
public class IngredientController {



    @PostMapping("/add")
    public ResponseEntity postIngredient() {    //재료 등록(유저의 재료목록에 추가)
        return null;
    }

    @PostMapping("/request")
    public ResponseEntity requestIngredient() {    //재료 추가 요청(마이페이지)
        return null;
    }

    @GetMapping
    public ResponseEntity getIngredients() {    //재료 목록 조회(사용자가 추가한 재료 목록을 불러옴)
        return null;
    }
    @DeleteMapping("/delete/{ingre-id}")
    public ResponseEntity deleteIngredient() {    //재료 삭제
        return null;
    }
}
