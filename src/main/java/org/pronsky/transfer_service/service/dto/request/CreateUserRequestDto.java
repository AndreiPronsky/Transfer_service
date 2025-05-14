package org.pronsky.transfer_service.service.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateUserRequestDto {

    private String name;
    private String dateOfBirth;
    private String password;
    private String phoneNumber;
    private String email;
}
