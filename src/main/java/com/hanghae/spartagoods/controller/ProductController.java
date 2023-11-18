package com.hanghae.spartagoods.controller;

import com.hanghae.spartagoods.dto.ProductRequestDto;
import com.hanghae.spartagoods.dto.ProductResponseDto;
import com.hanghae.spartagoods.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/products")
    public ProductResponseDto registerProducts(@RequestBody ProductRequestDto requestDto, @CookieValue(value = "Authorization", required = false) String jwt) {
        return productService.registerProducts(requestDto, jwt);
    }

    @GetMapping("/products")
    public void getProductsList() {

    }

    @GetMapping("/products/{id}")
    public void getProducts() {

    }
}
