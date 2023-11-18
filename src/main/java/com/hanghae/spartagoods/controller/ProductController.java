package com.hanghae.spartagoods.controller;

import com.hanghae.spartagoods.service.MemberService;
import com.hanghae.spartagoods.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/products")
    public void registerProducts() {

    }

    @GetMapping("/products")
    public void getProductsList() {

    }

    @GetMapping("/products/{id}")
    public void getProducts() {

    }
}
