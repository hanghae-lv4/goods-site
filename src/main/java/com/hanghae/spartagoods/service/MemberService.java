package com.hanghae.spartagoods.service;

import com.hanghae.spartagoods.dto.SignupRequestDto;
import com.hanghae.spartagoods.entity.Member;
import com.hanghae.spartagoods.jwt.JwtUtil;
import com.hanghae.spartagoods.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    // 비밀번호 유효성 검사를 위한 정규표현식
    private final String passwordRegex = "^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[!@#$%^&*()-_=+]).{8,15}$";
    public ResponseEntity<String> signup(SignupRequestDto requestDto) {
        // Email 중복 확인
        String email = requestDto.getEmail();
        Optional<Member> checkEmail = memberRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 Email");
        }

        // 비밀번호 유효성 검사
        String password = requestDto.getPassword();
        if (!password.matches(passwordRegex)) {
            throw new IllegalArgumentException("유효하지 않은 비밀번호");
        }

        String encodedPassword = passwordEncoder.encode(password);
        Member member = new Member(requestDto);
        member.setPassword(encodedPassword);
        memberRepository.save(member);

        return ResponseEntity.status(201).body("회원가입 성공");
    }
}
