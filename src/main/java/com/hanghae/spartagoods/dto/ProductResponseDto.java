package com.hanghae.spartagoods.dto;


import com.hanghae.spartagoods.entity.Product;
import lombok.Getter;

@Getter
public class ProductResponseDto {
    private String name;
    private Integer price;
    private Integer stock;
    private String intro;
    private String category;

    public ProductResponseDto(Product product) {
        this.name = product.getName();
        this.price = product.getPrice();
        this.stock = product.getStock();
        this.intro = product.getIntro();
        this.category = product.getCategory();
    }

}
