package com.hanghae.spartagoods.repository;

import com.hanghae.spartagoods.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
