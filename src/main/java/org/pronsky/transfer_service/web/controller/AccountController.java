package org.pronsky.transfer_service.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.pronsky.transfer_service.service.AccountService;
import org.pronsky.transfer_service.service.dto.request.TransferRequestDto;
import org.pronsky.transfer_service.service.dto.response.ExceptionResponseDto;
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
@Tag(name = "Account management", description = "`Account API`")
public class AccountController {

    private final AccountService accountService;

    @Operation(summary = "'Perform money transfer between two accounts'",
            description = """
                    '
                    Request body must contain sender and recipient IDs and the amount to transfer.
                    Sum cannot be negative.
                    '
                    """,
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TransferRequestDto.class))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "'Transfer successfully handled'",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema())),
            @ApiResponse(responseCode = "400", description = "'Bad Request'",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponseDto.class))),
            @ApiResponse(responseCode = "403", description = "'Forbidden'",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "'User Not Found'",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "'Internal Server Error'",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponseDto.class))),
    })
    @PostMapping("/transfer")
    public ResponseEntity<Void> performTransfer(@RequestBody @Valid TransferRequestDto request) {
        accountService.performTransfer(request);
        return ResponseEntity.accepted().build();
    }
}
