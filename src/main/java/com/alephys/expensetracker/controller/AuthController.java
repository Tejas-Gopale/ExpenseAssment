package com.alephys.expensetracker.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.alephys.expensetracker.dto.UserDto;
import com.alephys.expensetracker.dto.UserLoginDto;
import com.alephys.expensetracker.dto.UserLoginResponseDto;
import com.alephys.expensetracker.dto.UserSignUpDto;
import com.alephys.expensetracker.serviceImplementation.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController	
@Slf4j
@RequestMapping("/auth/v1")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@RequestBody @Valid UserSignUpDto signupDto) {
         log.info("SignUp controller Calling");
         UserDto user=   authService.signUp(signupDto);
      return ResponseEntity.ok(user);
    }

    @PostMapping("/signin")
    public ResponseEntity<UserLoginResponseDto> signin(@RequestBody @Valid UserLoginDto loginDto) {
    	log.info("Sign in Controller ... ");
    	UserLoginResponseDto response = authService.login(loginDto);
        return ResponseEntity.ok(response);
    }

    
}