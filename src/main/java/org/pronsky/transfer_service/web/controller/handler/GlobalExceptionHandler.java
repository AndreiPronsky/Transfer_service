package org.pronsky.transfer_service.web.controller.handler;

import jakarta.persistence.EntityNotFoundException;
import org.pronsky.transfer_service.service.dto.response.ExceptionResponseDto;
import org.pronsky.transfer_service.service.exception.TransferException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ExceptionResponseDto> handleNotFound(EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(buildExceptionResponseDto(e.getMessage(), e.getClass().getSimpleName()));
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponseDto> handleInternalServerError(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(buildExceptionResponseDto(e.getMessage(), e.getClass().getSimpleName()));
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponseDto> handleForbidden(SecurityException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(buildExceptionResponseDto(e.getMessage(), e.getClass().getSimpleName()));
    }

    @ExceptionHandler({
            TransferException.class,
            IllegalArgumentException.class
    })
    public ResponseEntity<ExceptionResponseDto> handleBadRequest(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(buildExceptionResponseDto(e.getMessage(), e.getClass().getSimpleName()));
    }

    private ExceptionResponseDto buildExceptionResponseDto(String message, String exceptionClass) {
        return ExceptionResponseDto.builder()
                .timestamp(LocalDateTime.now())
                .message(message)
                .className(exceptionClass)
                .build();
    }
}
