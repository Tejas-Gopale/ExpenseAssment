package com.alephys.expensetracker.serviceImplementation;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.alephys.expensetracker.dto.UserDto;
import com.alephys.expensetracker.dto.UserLoginDto;
import com.alephys.expensetracker.dto.UserLoginResponseDto;
import com.alephys.expensetracker.dto.UserSignUpDto;
import com.alephys.expensetracker.entity.User;
import com.alephys.expensetracker.exceptions.ResourceNotFoundException;
import com.alephys.expensetracker.exceptions.UserAlreadyExistsException;
import com.alephys.expensetracker.repository.UsersRepository;
import com.alephys.expensetracker.service.CustomUserDetailsService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService userService;
    private final UsersRepository usersRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    
    public UserLoginResponseDto login(UserLoginDto loginDto) {
        log.info("Login Sign in Service : {}", loginDto.toString());
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
            
            User user = (User) authentication.getPrincipal();
            String accessToken = jwtService.generateAccessToken(user);
     
            return new UserLoginResponseDto(accessToken);
        } catch (Exception e) {
            log.error("Authentication failed: {}", e.getMessage());
            throw new RuntimeException("Authentication failed");
        }
    }
    @Transactional			
    public UserDto signUp(UserSignUpDto signUpDto) {
        if (usersRepository.findByEmail(signUpDto.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User with email already exists: " + signUpDto.getEmail());
        }
        
        User toBeCreate = modelMapper.map(signUpDto, User.class);
        toBeCreate.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        
        User savedUser = usersRepository.save(toBeCreate);
        return modelMapper.map(savedUser, UserDto.class);
    }
}