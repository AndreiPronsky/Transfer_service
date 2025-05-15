package org.pronsky.transfer_service.service.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;


@Getter
@Builder
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
