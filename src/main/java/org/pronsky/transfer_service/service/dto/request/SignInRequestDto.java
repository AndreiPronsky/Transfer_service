package org.pronsky.transfer_service.service.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignInRequestDto {

    private String email;
    private String password;
}
