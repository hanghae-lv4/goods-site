package com.hanghae.spartagoods.service;

import com.hanghae.spartagoods.dto.BasketRequestDto;
import com.hanghae.spartagoods.entity.Basket;
import com.hanghae.spartagoods.entity.Member;
import com.hanghae.spartagoods.entity.Product;
import com.hanghae.spartagoods.jwt.TokenManager;
import com.hanghae.spartagoods.jwt.TokenValidator;
import com.hanghae.spartagoods.repository.BasketRepository;
import com.hanghae.spartagoods.repository.MemberRepository;
import com.hanghae.spartagoods.repository.ProductRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BasketService {
    private final BasketRepository basketRepository;
    private final TokenManager tokenManager;
    private final TokenValidator tokenValidator;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    public String addToBasket(Long productId, BasketRequestDto requestDto, HttpServletRequest request) {

        Member member = (Member) request.getAttribute("member");
        Product product = productRepository.findById(productId).orElseThrow(() ->
            new IllegalArgumentException("선택한 상품이 존재하지 않습니다.")
        );

        Basket basket = new Basket(member, product, requestDto.getAmount());

        basketRepository.save(basket);

        return "상품을 장바구니에 추가하였습니다.";
    }

//    private Member findMember(String jwt) {
//        String token = tokenValidator.substringToken(jwt);
//        System.out.println(token);
//        Claims claims = tokenManager.getMemberInfoFromToken(token);
//        String memberEmail = claims.getSubject();
//
//        return memberRepository.findByEmail(memberEmail).orElseThrow(() ->
//            new IllegalArgumentException("존재하지 않는 회원입니다.")
//        );
//    }
}
