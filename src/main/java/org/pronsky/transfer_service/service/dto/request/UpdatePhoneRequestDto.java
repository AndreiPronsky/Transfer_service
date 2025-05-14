package org.pronsky.transfer_service.service.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePhoneRequestDto {

    private Long userId;
    private Long phoneId;
    private String newPhoneNumber;

}
