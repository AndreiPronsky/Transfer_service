package org.pronsky.transfer_service.service.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;


@Getter
@Builder
@Schema(description = "Data Transfer Object to perform a transfer between accounts")
public class TransferRequestDto {

    @NotNull(message = "Sender account ID must not be null")
    @Positive(message = "Sender account ID must be a positive number")
    private Long senderId;

    @NotNull(message = "Recipient account ID must not be null")
    @Positive(message = "Recipient account ID must be a positive number")
    private Long recipientId;

    @NotNull(message = "Transfer amount must not be null")
    @DecimalMin(value = "0.01", message = "Transfer amount must be at least 0.01")
    private BigDecimal amount;
}
