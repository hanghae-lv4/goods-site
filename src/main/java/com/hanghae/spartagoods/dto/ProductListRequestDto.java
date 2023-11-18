package com.hanghae.spartagoods.dto;

import lombok.Getter;

@Getter
public class ProductListRequestDto {
    private Integer page;
    private Integer size;
    private String sortBy;
    private Boolean isAsc;
}
