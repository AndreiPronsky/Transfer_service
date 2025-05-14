package org.pronsky.transfer_service.service.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddPhoneToUserRequestDto {

    private Long userId;
    private String phoneNumber;
}
