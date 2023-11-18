package com.hanghae.spartagoods.dto;

import com.hanghae.spartagoods.entity.Product;
import java.util.List;
import lombok.Getter;
@Getter

public class BasketTotalResponseDto {
    private List<Product> basketItems;
    private int amount;

    public BasketTotalResponseDto(List<Product> products, int amount) {
        this.basketItems = products;
        this.amount = amount;
    }
}
