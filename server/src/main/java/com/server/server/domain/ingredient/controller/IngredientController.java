package com.server.server.domain.ingredient.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ingres")
public class IngredientController {



    @PostMapping("/add")
    public ResponseEntity postIngredient() {    //재료 등록
        return null;
    }
}
