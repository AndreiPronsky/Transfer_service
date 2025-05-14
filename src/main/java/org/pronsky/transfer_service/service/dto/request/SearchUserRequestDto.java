package org.pronsky.transfer_service.service.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SearchUserRequestDto {

    private String dateOfBirth;
    private String phone;
    private String name;
    private String email;
}
