package com.hanghae.spartagoods.controller;

import com.hanghae.spartagoods.dto.SignUpRequestDto;
import com.hanghae.spartagoods.service.MemberService;
import jakarta.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;


    // 회원 가입
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignUpRequestDto requestDto, BindingResult bindingResult) {
        // Validation 예외 처리
        ResponseEntity<String> error = vadlidException(bindingResult);
        if (error != null) {
            return error;
        }
        String successMessage = memberService.signup(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
            .header(
                HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE + ";charset=" + StandardCharsets.UTF_8)
            .body(successMessage);
    }

    @PostMapping("/signin")
    public void signin() {

    }

    private ResponseEntity<String> vadlidException(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> list = bindingResult.getFieldErrors();
            for(FieldError error : list) {
                return new ResponseEntity<>(error.getDefaultMessage(), HttpStatus.BAD_REQUEST);
            }
        }
        return null;
    }


}
