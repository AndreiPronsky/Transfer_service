package org.pronsky.transfer_service.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.pronsky.transfer_service.service.AccountService;
import org.pronsky.transfer_service.service.dto.request.TransferRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/accounts")
@Validated
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/transfer")
    public ResponseEntity<Void> performTransfer(@RequestBody @Valid TransferRequestDto request) {
        accountService.performTransfer(request);
        return ResponseEntity.accepted().build();
    }
}
