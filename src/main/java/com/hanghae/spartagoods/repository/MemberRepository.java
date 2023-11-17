package com.hanghae.spartagoods.repository;

import com.hanghae.spartagoods.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
