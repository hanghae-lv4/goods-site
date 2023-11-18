package com.hanghae.spartagoods.service;

import com.hanghae.spartagoods.dto.SigninRequestDto;
import com.hanghae.spartagoods.dto.SignupRequestDto;
import com.hanghae.spartagoods.entity.Member;
import com.hanghae.spartagoods.entity.MemberRoleEnum;
import com.hanghae.spartagoods.exception.EmailDuplicateException;
import com.hanghae.spartagoods.exception.EmailNotFoundException;
import com.hanghae.spartagoods.exception.NotFoundException;
import com.hanghae.spartagoods.exception.PasswordUnmatched;
import com.hanghae.spartagoods.jwt.TokenGenerator;
import com.hanghae.spartagoods.repository.MemberRepository;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenGenerator tokenGenerator;

    // 회원 가입
    public String signup(SignupRequestDto requestDto) {
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

    // 로그인
    public String signin(SigninRequestDto requestDto, HttpServletResponse res) {
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();

        // DB에 있는지 확인
        Member member = validateSignin(email, password);


        // 토큰 생성
        String token = tokenGenerator.createToken(email, member.getRole());
        tokenGenerator.addJwtToCookie(token, res);

        return "로그인 하였습니다.";
    }

    // 이메일 중복 확인
    private void duplicateEmail(Optional<Member> checkEmail) {
        checkEmail.ifPresent(member -> {
            throw new EmailDuplicateException("중복된 이메일 입니다.");
        });
    }

    // 이메일과 비밀번호 확인
    private Member validateSignin(String email, String password) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() ->
            new NotFoundException("등록된 이메일이 없습니다.")
        );

        if(!passwordEncoder.matches(password,member.getPassword())) {
            throw new PasswordUnmatched("비밀번호가 일치하지 않습니다.");
        }

        return member;
    }
}
