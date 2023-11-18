package com.hanghae.spartagoods.repository;

import com.hanghae.spartagoods.entity.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BasketRepository extends JpaRepository<Basket, Long> {
    List<Basket> findByMember_Id(Long memberId);
    Optional<Basket> findByMember_IdAndProduct_Id(Long memberId, Long productId);
}
