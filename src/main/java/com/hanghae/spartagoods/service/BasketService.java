package com.hanghae.spartagoods.service;

import com.hanghae.spartagoods.dto.BasketRequestDto;
import com.hanghae.spartagoods.dto.BasketTotalResponseDto;
import com.hanghae.spartagoods.entity.Basket;
import com.hanghae.spartagoods.entity.Member;
import com.hanghae.spartagoods.entity.Product;
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
    public String addToBasket(Long productId, BasketRequestDto requestDto, HttpServletRequest request) {

        Member member = (Member) request.getAttribute("member");
        Product product = getProduct(productId);

        Basket basket = new Basket(member, product, requestDto.getAmount());

        basketRepository.save(basket);

        return "상품을 장바구니에 추가하였습니다.";
    }

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

    @Transactional
    public String updateBasket(Long productId, BasketRequestDto requestDto, HttpServletRequest request) {
        Member member = (Member) request.getAttribute("member");
        Product product = getProduct(productId);
        Basket basket = basketRepository.findByMemberIdAndProductId(member.getId(), product.getId());
        basket.updateAmount(requestDto);

        return "성공적으로 수정하였습니다.";
    }

    private Product getProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() ->
            new IllegalArgumentException("선택한 상품이 존재하지 않습니다.")
        );
        return product;
    }
}
