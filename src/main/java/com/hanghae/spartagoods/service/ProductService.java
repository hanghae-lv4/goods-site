package com.hanghae.spartagoods.service;

import com.hanghae.spartagoods.jwt.AdminAccess;
import com.hanghae.spartagoods.dto.ProductRequestDto;
import com.hanghae.spartagoods.dto.ProductResponseDto;
import com.hanghae.spartagoods.entity.Product;
import com.hanghae.spartagoods.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    @AdminAccess
    public ProductResponseDto registerProducts(ProductRequestDto requestDto, String jwt)
    {
        Product product = new Product(requestDto);
        return new ProductResponseDto(productRepository.save(product));
    }

}
