package com.hanghae.spartagoods.dto;

import com.hanghae.spartagoods.entity.Basket;
import com.hanghae.spartagoods.entity.Product;
import lombok.Getter;

@Getter
public class BasketResponseDto {
    private String productName;
    private Integer productPrice;
    private Integer productAmount;

    public BasketResponseDto(Basket basket, Product product) {
        this.productName = product.getName();
        this.productPrice = product.getPrice();
        this.productAmount = basket.getAmount();
    }
}
