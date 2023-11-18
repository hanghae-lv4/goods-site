package com.hanghae.spartagoods.entity;

import com.hanghae.spartagoods.dto.SignupRequestDto;
import jakarta.persistence.*;
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

    @OneToMany(mappedBy = "member", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Basket> memberBasket = new ArrayList<>();

    public Member(SignupRequestDto requestDto) {
        this.email = requestDto.getEmail();
        this.gender = requestDto.getGender();
        this.phone = requestDto.getPhone();
        this.address = requestDto.getAddress();
        this.role = MemberRoleEnum.valueOf(requestDto.getRole());
    }
}
