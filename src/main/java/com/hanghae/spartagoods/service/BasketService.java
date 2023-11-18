package com.hanghae.spartagoods.service;

import com.hanghae.spartagoods.dto.BasketListResponseDto;
import com.hanghae.spartagoods.dto.BasketRequestDto;
import com.hanghae.spartagoods.dto.BasketResponseDto;
import com.hanghae.spartagoods.dto.BasketUpdateRequestDto;
import com.hanghae.spartagoods.entity.Basket;
import com.hanghae.spartagoods.entity.Member;
import com.hanghae.spartagoods.entity.Product;
import com.hanghae.spartagoods.repository.BasketRepository;
import com.hanghae.spartagoods.repository.ProductRepository;
import com.hanghae.spartagoods.security.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BasketService {
    private final ProductRepository productRepository;
    private final BasketRepository basketRepository;

    @Transactional
    public ResponseEntity<String> addToBasket(BasketRequestDto requestDto, MemberDetails memberDetails) {
        Member member = memberDetails.getMember();
        Product product = productRepository.findById(requestDto.getProductId()).orElseThrow(
                () -> new IllegalArgumentException("상품이 존재하지 않음")
        );

        if (product.getStock() < requestDto.getAmount()) {
            throw new IllegalArgumentException("상품의 재고가 부족함");
        }
        product.setStock(product.getStock() - requestDto.getAmount());
        Basket basket = new Basket(requestDto, member, product);
        basketRepository.save(basket);
        productRepository.save(product);
        return ResponseEntity.ok().body("장바구니에 품목 추가 완료");
    }

    public ResponseEntity<BasketListResponseDto> getBasket(MemberDetails memberDetails) {
        Member member = memberDetails.getMember();
        List<Basket> basketList = basketRepository.findByMember_Id(member.getId());

        int totalPrice = 0;
        List<BasketResponseDto> basketResponseDtoList = new ArrayList<>();
        for (Basket basket : basketList) {
            int productPrice = basket.getProduct().getPrice();
            int productAmount = basket.getAmount();
            basketResponseDtoList.add(new BasketResponseDto(basket, basket.getProduct()));
            totalPrice += productPrice * productAmount;
        }

        return ResponseEntity.ok().body(new BasketListResponseDto(basketResponseDtoList, totalPrice));
    }

    @Transactional
    public ResponseEntity<String> updateBasket(Long productId, BasketUpdateRequestDto requestDto, MemberDetails memberDetails) {
        Basket basket = basketRepository.findByMember_IdAndProduct_Id(memberDetails.getMember().getId(), productId).orElseThrow(
                () -> new IllegalArgumentException("장바구니에 없음")
        );
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new IllegalArgumentException("상품이 없음")
        );
        if (product.getStock() < requestDto.getAmount() - basket.getAmount()) {
            throw new IllegalArgumentException("재고가 부족함");
        }
        product.setStock(product.getStock() + basket.getAmount() - requestDto.getAmount());
        basket.setAmount(requestDto.getAmount());
        return ResponseEntity.ok().body("장바구니에서 품목 수량 수정 완료");
    }

    @Transactional
    public ResponseEntity<String> deleteBasket(Long productId, MemberDetails memberDetails) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new IllegalArgumentException("상품이 존재하지 않음")
        );
        Member member = memberDetails.getMember();
        Basket basket = basketRepository.findByMember_IdAndProduct_Id(member.getId(), product.getId()).orElseThrow(
                () -> new IllegalArgumentException("장바구니에 없는 품목")
        );

        product.setStock(product.getStock() + basket.getAmount());
        basketRepository.delete(basket);
        productRepository.save(product);
        return ResponseEntity.ok().body("장바구니에서 품목 삭제 완료");    }
}
