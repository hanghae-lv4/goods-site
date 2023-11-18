package com.hanghae.spartagoods.entity;

import com.hanghae.spartagoods.dto.BasketRequestDto;
import com.hanghae.spartagoods.dto.BasketUpdateRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "basket")
@NoArgsConstructor
public class Basket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer amount;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public Basket(BasketRequestDto requestDto, Member member, Product product) {
        this.amount = requestDto.getAmount();
        this.member = member;
        this.product = product;
    }
}
