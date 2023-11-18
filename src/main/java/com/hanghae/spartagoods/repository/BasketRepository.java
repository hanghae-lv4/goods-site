package com.hanghae.spartagoods.repository;

import com.hanghae.spartagoods.entity.Basket;
import com.hanghae.spartagoods.entity.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketRepository extends JpaRepository<Basket, Long> {
    List<Basket> findAllByMemberId(Long memberId);

    Optional<Basket> findByMemberIdAndProductId(Long memberId, Long productId);
}
