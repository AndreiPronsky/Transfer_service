package org.pronsky.transfer_service.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.pronsky.transfer_service.data.entity.User;
import org.pronsky.transfer_service.data.repository.UserRepository;
import org.pronsky.transfer_service.service.dto.request.SignInRequestDto;
import org.pronsky.transfer_service.service.dto.response.JwtTokenResponse;
import org.pronsky.transfer_service.service.security.JwtSecurityService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtSecurityService jwtSecurityService;

    @Transactional
    public JwtTokenResponse signIn(SignInRequestDto request) {

        User user = userRepository.findByPrimaryEmail(request.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("User with email " + request.getEmail() + " not found"));

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(), request.getPassword()));

        String jwtToken = jwtSecurityService.generateToken(user);

        return JwtTokenResponse.builder()
                .token(jwtToken)
                .build();
    }
}
