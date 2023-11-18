package com.hanghae.spartagoods.service;

import com.hanghae.spartagoods.dto.ProductRequestDto;
import com.hanghae.spartagoods.dto.ProductResponseDto;
import com.hanghae.spartagoods.entity.Product;
import com.hanghae.spartagoods.exception.NotFoundException;
import com.hanghae.spartagoods.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;


    // 상품 등록
    public ProductResponseDto registerProducts(ProductRequestDto requestDto)
    {
        Product product = new Product(requestDto);
        return new ProductResponseDto(productRepository.save(product));
    }

    // 상품 조회
    public Page<ProductResponseDto> getProductsList(int page, int size, String sortBy, boolean isAsc) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Product> productList = productRepository.findAll(pageable);

        return productList.map(ProductResponseDto::new);
    }

    // 선택한 상품 조회
    public Product getProduct(Long id) {
        return productRepository.findById(id).orElseThrow(() ->
            new NotFoundException("선택한 상품이 존재하지 않습니다.")
        );
    }
}
