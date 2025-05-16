package org.pronsky.transfer_service.service.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Exception response")
public class ExceptionResponseDto {

    @Schema(description = "Exception timestamp")
    private LocalDateTime timestamp;

    @Schema(description = "Exception class name")
    private String className;

    @Schema(description = "Exception message")
    private String message;
}
