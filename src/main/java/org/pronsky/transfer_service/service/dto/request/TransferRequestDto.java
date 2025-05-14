package org.pronsky.transfer_service.service.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class TransferRequestDto {

    private Long transferFrom;
    private Long transferTo;
    private BigDecimal amount;
}
