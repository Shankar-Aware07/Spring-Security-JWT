package com.example.security.controller;

import com.example.security.dto.LoginUserDto;
import com.example.security.dto.RegisterUserDto;
import com.example.security.dto.VerifyUserDto;
import com.example.security.model.User;
import com.example.security.responses.LoginResponse;
import com.example.security.service.AuthenticationService;
import com.example.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserDto registerUserDto){
        User registerUser = authenticationService.signUp(registerUserDto);
        return ResponseEntity.ok(registerUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto){
        User authenticatedUser = authenticationService.authenticate(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        LoginResponse loginResponse = new LoginResponse(jwtToken, jwtService.getExpirationTime());
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestBody VerifyUserDto verifyUserDto){
        try {
            authenticationService.verifyUser(verifyUserDto);
            return ResponseEntity.ok("Account verified successfully");
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/resend")
    public ResponseEntity<?> resendVerificationCode(@RequestParam String email){
        try {
            authenticationService.resendVerificationCode(email);
            return ResponseEntity.ok("Verification code sent");
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
