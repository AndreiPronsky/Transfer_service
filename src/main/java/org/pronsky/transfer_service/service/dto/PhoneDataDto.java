package org.pronsky.transfer_service.service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PhoneDataDto {

    private Long id;
    private String phoneNumber;
}
