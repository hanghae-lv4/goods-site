package com.hanghae.spartagoods.service;

import com.hanghae.spartagoods.dto.SignUpRequestDto;
import com.hanghae.spartagoods.entity.Member;
import com.hanghae.spartagoods.entity.MemberRoleEnum;
import com.hanghae.spartagoods.repository.MemberRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    public String signup(SignUpRequestDto requestDto) {
        String email = requestDto.getEmail();

        // 이메일 중복 확인
        Optional<Member> checkEmail = memberRepository.findByEmail(email);
        duplicateEmail(checkEmail);


        // 비밀번호 암호화
        String password = passwordEncoder.encode(requestDto.getPassword());


        // Admin 권한 체크
        MemberRoleEnum role = requestDto.isAdmin() ? MemberRoleEnum.ADMIN : MemberRoleEnum.USER;

        // DB에 저장
        Member member = new Member(email, password, role, requestDto);
        memberRepository.save(member);

        return "가입에 성공하였습니다.";
    }







    private void duplicateEmail(Optional<Member> checkEmail) {
        checkEmail.ifPresent(member -> {
            throw new IllegalArgumentException("중복된 이메일 입니다.");
        });
    }
}
