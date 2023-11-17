package com.hanghae.spartagoods.controller;

import com.hanghae.spartagoods.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public void signup() {

    }

    @PostMapping("/signin")
    public void signin() {

    }


}
