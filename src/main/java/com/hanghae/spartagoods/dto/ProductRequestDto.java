package com.hanghae.spartagoods.dto;

import lombok.Getter;

@Getter
public class ProductRequestDto {
    private String name;
    private Integer price;
    private Integer stock;
    private String intro;
    private String category;

}
