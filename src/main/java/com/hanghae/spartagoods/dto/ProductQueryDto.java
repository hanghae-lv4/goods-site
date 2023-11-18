package com.hanghae.spartagoods.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductQueryDto {
    private int page;
    private int size;
    private String sortBy;
    private boolean asc;
}
