package com.idea.psychiatry.modules.auth.service;


import com.idea.psychiatry.modules.auth.config.properties.JwtProperties;
import com.idea.psychiatry.modules.auth.dto.LoginRequest;
import com.idea.psychiatry.modules.auth.dto.LoginResponse;
import com.idea.psychiatry.modules.auth.security.CustomUserPrincipal;
import com.idea.psychiatry.modules.auth.security.JwtService;
import com.idea.psychiatry.modules.user.entity.User;
import com.idea.psychiatry.modules.user.repository.UserRepository;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final JwtProperties jwtProperties;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, JwtProperties jwtProperties) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.jwtProperties = jwtProperties;
    }

    public LoginResponse login(LoginRequest request) {
        // ۱. پیدا کردن کاربر — ایمیل اشتباه و رمز اشتباه هر دو یک خطا می‌دن (امنیت)
        User user = userRepository.findByEmail(request.mobile().toLowerCase().trim()).orElseThrow(() -> new BadCredentialsException("Invalid email or password"));

        // ۲. چک فعال بودن کاربر
        if (!user.isActive()) {
            throw new BadCredentialsException("Invalid email or password");
        }

        // ۳. بررسی رمز عبور
        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new BadCredentialsException("Invalid email or password");
        }

        // ۴. تولید JWT
        CustomUserPrincipal principal = new CustomUserPrincipal(user);
        String accessToken = jwtService.generateAccessToken(principal);

        return new LoginResponse(accessToken, "Bearer", jwtProperties.expirationMs() / 1000,  // تبدیل ms به ثانیه
                user.getId(), user.getOrganizationId(), user.getEmail(), user.getRole());
    }
}
