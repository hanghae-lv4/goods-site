package com.hanghae.spartagoods.controller;

import com.hanghae.spartagoods.dto.ProductListRequestDto;
import com.hanghae.spartagoods.dto.ProductRequestDto;
import com.hanghae.spartagoods.dto.ProductResponseDto;
import com.hanghae.spartagoods.entity.Product;
import com.hanghae.spartagoods.security.MemberDetails;
import com.hanghae.spartagoods.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/products")
    public ResponseEntity<String> registerProduct(@RequestBody ProductRequestDto requestDto,
                                                  @AuthenticationPrincipal MemberDetails memberDetails) {
        return productService.registerProduct(requestDto, memberDetails);
    }

    @GetMapping("/products")
    public ResponseEntity<Page<ProductResponseDto>> getProductList(@RequestBody ProductListRequestDto requestDto) {
        return productService.getProductList(requestDto);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductResponseDto> getProduct(@PathVariable Long id) {
        return productService.getProduct(id);
    }
}
