package com.example.givenget.model;

public record SignupRequest(
    String name,
    String email,
    String phoneNum,
    String password,  // Plain password to be encoded
    String location
) {}
