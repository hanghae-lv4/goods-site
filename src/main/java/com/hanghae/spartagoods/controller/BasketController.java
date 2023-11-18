package com.hanghae.spartagoods.controller;

import com.hanghae.spartagoods.dto.BasketListResponseDto;
import com.hanghae.spartagoods.dto.BasketRequestDto;
import com.hanghae.spartagoods.dto.BasketResponseDto;
import com.hanghae.spartagoods.dto.BasketUpdateRequestDto;
import com.hanghae.spartagoods.security.MemberDetails;
import com.hanghae.spartagoods.service.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BasketController {

    private final BasketService basketService;

    @PostMapping("/baskets")
    public ResponseEntity<String> addToBaskets(@RequestBody BasketRequestDto requestDto,
                             @AuthenticationPrincipal MemberDetails memberDetails) {
        return basketService.addToBasket(requestDto, memberDetails);
    }

    @GetMapping("/baskets")
    public ResponseEntity<BasketListResponseDto> getBasket(@AuthenticationPrincipal MemberDetails memberDetails) {
        return basketService.getBasket(memberDetails);
    }

    @PutMapping("/baskets/{productId}")
    public ResponseEntity<String> updateBasket(@PathVariable Long productId,
                                                          @RequestBody BasketUpdateRequestDto requestDto,
                                                          @AuthenticationPrincipal MemberDetails memberDetails) {
        return basketService.updateBasket(productId, requestDto, memberDetails);
    }

    @DeleteMapping("/baskets/{productId}")
    public ResponseEntity<String> deleteBasket(@PathVariable Long productId,
                                               @AuthenticationPrincipal MemberDetails memberDetails) {
        return basketService.deleteBasket(productId, memberDetails);
    }

}
