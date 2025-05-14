package org.pronsky.transfer_service.service.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class TransferResponseDto {

    private BigDecimal amount;
    private String transferTo;
    private String transferFrom;
}
