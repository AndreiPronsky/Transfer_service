package org.pronsky.transfer_service.service.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JwtTokenResponse {

    private String token;
}
