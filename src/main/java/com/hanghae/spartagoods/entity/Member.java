package com.hanghae.spartagoods.entity;

import com.hanghae.spartagoods.dto.SignUpRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "member")
@NoArgsConstructor

public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private char gender;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private MemberRoleEnum role;

    @OneToMany(mappedBy = "member")
    private List<Basket> basket = new ArrayList<>();

    public Member(String email, String password, MemberRoleEnum role, SignUpRequestDto requestDto) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.gender = requestDto.getGender();
        this.phone = requestDto.getPhone();
        this.address = requestDto.getAddress();
    }
}
