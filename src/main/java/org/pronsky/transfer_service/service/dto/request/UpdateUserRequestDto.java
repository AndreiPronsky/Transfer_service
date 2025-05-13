package org.pronsky.transfer_service.service.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateUserRequestDto {

    private Long id;
    private String phoneNumber;
    private String email;
}
