package com.hanghae.spartagoods.service;

import com.hanghae.spartagoods.dto.BasketRequestDto;
import com.hanghae.spartagoods.dto.BasketTotalResponseDto;
import com.hanghae.spartagoods.entity.Basket;
import com.hanghae.spartagoods.entity.Member;
import com.hanghae.spartagoods.entity.Product;
import com.hanghae.spartagoods.exception.NotFoundException;
import com.hanghae.spartagoods.repository.BasketRepository;
import com.hanghae.spartagoods.repository.ProductRepository;
import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BasketService {
    private final BasketRepository basketRepository;
    private final ProductRepository productRepository;

    // 장바구니 상품 추가
    public String addToBasket(Long productId, BasketRequestDto requestDto, HttpServletRequest request) {

        Member member = (Member) request.getAttribute("member");
        Product product = getProduct(productId);

        Basket basket = new Basket(member, product, requestDto.getAmount());

        basketRepository.save(basket);

        return "상품을 장바구니에 추가하였습니다.";
    }


    // 장바구니 상품 조회
    public BasketTotalResponseDto getBasket(HttpServletRequest request) {
        Member member = (Member) request.getAttribute("member");
        List<Basket> basketItems = basketRepository.findAllByMemberId(member.getId());

        int amount = 0;
        List<Product> products = new ArrayList<>();

        for (Basket basketItem : basketItems) {
            Product product = getProduct(basketItem.getProduct().getId());
            products.add(product);
            amount += product.getPrice() * basketItem.getAmount();
        }

        return new BasketTotalResponseDto(products, amount);
    }


    // 장바구니 상품 수량 수정
    @Transactional
    public String updateBasket(Long productId, BasketRequestDto requestDto, HttpServletRequest request) {
        Member member = (Member) request.getAttribute("member");
        Product product = getProduct(productId);
        Basket basket = getBasket(member, product);
        basket.updateAmount(requestDto);

        return "성공적으로 수정하였습니다.";
    }


    // 장바구니 상품 삭제
    public String removeBasket(Long productId, HttpServletRequest request) {
        Member member = (Member) request.getAttribute("member");
        Product product = getProduct(productId);
        Basket basket = getBasket(member, product);
        basketRepository.delete(basket);

        return "장바구니에서 상품을 삭제하였습니다.";
    }


    // DB에서 장바구니 가져오기
    private Basket getBasket(Member member, Product product) {
        return basketRepository.findByMemberIdAndProductId(member.getId(), product.getId()).orElseThrow(() ->
            new NotFoundException("장바구니에 상품이 없습니다.")
        );
    }

    // DB에서 상품 가져오기
    private Product getProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() ->
            new NotFoundException("선택한 상품이 존재하지 않습니다.")
        );
        return product;
    }
}
