package org.pronsky.transfer_service.service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmailDataDto {

    private Long id;
    private String email;
}
