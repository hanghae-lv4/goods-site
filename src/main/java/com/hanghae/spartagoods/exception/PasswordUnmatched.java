package com.hanghae.spartagoods.exception;

public class PasswordUnmatched extends RuntimeException{
    public PasswordUnmatched(String message) {
        super(message);
    }
}