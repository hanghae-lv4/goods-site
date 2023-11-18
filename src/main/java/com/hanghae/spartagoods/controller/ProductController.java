package com.hanghae.spartagoods.controller;

import com.hanghae.spartagoods.dto.ProductQueryDto;
import com.hanghae.spartagoods.dto.ProductRequestDto;
import com.hanghae.spartagoods.dto.ProductResponseDto;
import com.hanghae.spartagoods.entity.Product;
import com.hanghae.spartagoods.service.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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
    public Page<ProductResponseDto> getProductList(
        @ModelAttribute ProductQueryDto query) {
        return productService.getProductsList(query.getPage() - 1, query.getSize(), query.getSortBy(), query.isAsc());
    }

    @GetMapping("/products/{id}")
    public Product getProduct(@PathVariable Long id) {
        return productService.getProduct(id);
    }
}
