package com.hanghae.spartagoods.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class BasketListResponseDto {
    private List<BasketResponseDto> basketResponseDtoList;
    private Integer totalPrice;

    public BasketListResponseDto(List<BasketResponseDto> basketResponseDtoList, int totalPrice) {
        this.basketResponseDtoList = basketResponseDtoList.stream().toList();
        this.totalPrice = totalPrice;
    }
}
