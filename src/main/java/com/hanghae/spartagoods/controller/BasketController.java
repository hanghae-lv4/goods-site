package com.hanghae.spartagoods.controller;

import com.hanghae.spartagoods.dto.BasketRequestDto;
import com.hanghae.spartagoods.dto.BasketTotalResponseDto;
import com.hanghae.spartagoods.service.BasketService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BasketController {

    private final BasketService basketService;

    @PostMapping("/baskets/{productId}")
    public ResponseEntity<String> addToBasket(@PathVariable Long productId, @RequestBody BasketRequestDto requestDto, HttpServletRequest request) {
        String successMessage = basketService.addToBasket(productId, requestDto, request);
        return ResponseEntity.status(HttpStatus.CREATED)
            .contentType(MediaType.TEXT_PLAIN)
            .body(successMessage);
    }

    @GetMapping("/baskets")
    public BasketTotalResponseDto  getBasket(HttpServletRequest request) {
        return basketService.getBasket(request);
    }

    @PutMapping("/baskets/{productId}")
    public ResponseEntity<String> updateBasket(@PathVariable Long productId, @RequestBody BasketRequestDto requestDto, HttpServletRequest request) {
        String successMessage = basketService.updateBasket(productId, requestDto, request);
        return ResponseEntity.status(HttpStatus.CREATED)
            .contentType(MediaType.TEXT_PLAIN)
            .body(successMessage);
    }

    @DeleteMapping("/baskets")
    public void deleteBaskets() {

    }

}
