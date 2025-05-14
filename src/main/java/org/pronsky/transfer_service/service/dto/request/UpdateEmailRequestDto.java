package org.pronsky.transfer_service.service.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateEmailRequestDto {

    private Long userId;
    private Long emailId;
    private String newEmail;
}
