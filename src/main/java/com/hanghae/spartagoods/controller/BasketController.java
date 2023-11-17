package com.hanghae.spartagoods.controller;

import com.hanghae.spartagoods.service.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BasketController {

    private final BasketService basketService;

    @PostMapping("/baskets")
    public void addToBaskets() {

    }

    @GetMapping("/baskets")
    public void getBaskets() {

    }

    @PutMapping("/baskets")
    public void updateBaskets() {

    }

    @DeleteMapping("/baskets")
    public void deleteBaskets() {

    }

}
