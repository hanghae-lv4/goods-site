package com.hanghae.spartagoods.service;

import com.hanghae.spartagoods.dto.ProductListRequestDto;
import com.hanghae.spartagoods.dto.ProductRequestDto;
import com.hanghae.spartagoods.dto.ProductResponseDto;
import com.hanghae.spartagoods.entity.Product;
import com.hanghae.spartagoods.repository.ProductRepository;
import com.hanghae.spartagoods.security.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public ResponseEntity<String> registerProduct(ProductRequestDto requestDto, MemberDetails memberDetails) {
        if (!StringUtils.equals(memberDetails.getMember().getRole(), "ADMIN")) {
            return ResponseEntity.badRequest().body("권한이 없음");
        }
        productRepository.save(new Product(requestDto));

        return ResponseEntity.ok().body("상품 등록 성공");
    }

    public ResponseEntity<ProductResponseDto> getProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new IllegalArgumentException("상품이 존재하지 않음")
        );

        return ResponseEntity.ok().body(new ProductResponseDto(product));
    }

    public ResponseEntity<Page<ProductResponseDto>> getProductList(ProductListRequestDto requestDto) {
        // 페이징
        Sort.Direction direction = requestDto.getIsAsc() ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, requestDto.getSortBy());
        Pageable pageable = PageRequest.of(requestDto.getPage() - 1, requestDto.getSize(), sort);
        Page<Product> productList = productRepository.findAll(pageable);

        return ResponseEntity.ok().body(productList.map(ProductResponseDto::new));
    }
}
