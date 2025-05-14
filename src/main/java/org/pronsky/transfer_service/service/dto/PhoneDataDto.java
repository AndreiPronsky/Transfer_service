package org.pronsky.transfer_service.service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PhoneDataDto {

    private Long emailId;
    private String phoneNumber;
}
