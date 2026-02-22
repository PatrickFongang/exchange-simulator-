package com.exchange_simulator.controller;

import com.exchange_simulator.mapper.UserMapper;
import com.exchange_simulator.dto.auth.AuthRequestDto;
import com.exchange_simulator.dto.user.UserRequestDto;
import com.exchange_simulator.dto.user.UserResponseDto;
import com.exchange_simulator.security.JwtUtils;
import com.exchange_simulator.service.BlacklistedTokenService;
import jakarta.servlet.http.HttpServletRequest;
import com.exchange_simulator.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final BlacklistedTokenService blacklistedTokenService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private final JwtUtils jwtUtils;

    @PostMapping("/registration")
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto userData) {
        var user = userService.createUser(userData);

        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> authUser(@RequestBody AuthRequestDto authRequest) {
        try {
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password());
            authenticationManager.authenticate(token);
            return ResponseEntity.ok(Map.of("token", jwtUtils.generateToken(authRequest.username())));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error","Invalid login or passwortd"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error","Server error: " + e.getMessage()));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String,String>> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if(authHeader != null && authHeader.startsWith("Bearer ")){
            String token = authHeader.substring(7);
            try{
                Instant expiresAt = jwtUtils.extractExpiration(token).toInstant();
                blacklistedTokenService.blacklistToken(token, expiresAt);
            }catch(Exception e){}
        }
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok(Map.of("message", "Successful logout"));
    }
}
