package org.pronsky.transfer_service.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.pronsky.transfer_service.service.AuthService;
import org.pronsky.transfer_service.service.dto.request.SignInRequestDto;
import org.pronsky.transfer_service.service.dto.response.JwtTokenResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public ResponseEntity<JwtTokenResponse> signIn(@RequestBody @Valid SignInRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.signIn(requestDto));
    }
}
