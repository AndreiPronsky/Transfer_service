package org.pronsky.transfer_service.util.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.pronsky.transfer_service.service.dto.request.SearchUserRequestDto;

public class AtLeastOneFieldValidator implements ConstraintValidator<AtLeastOneField, SearchUserRequestDto> {

    @Override
    public boolean isValid(SearchUserRequestDto requestDto, ConstraintValidatorContext context) {
        return requestDto != null &&
                (isNotBlank(requestDto.getName())
                        || isNotBlank(requestDto.getPhone())
                        || isNotBlank(requestDto.getDateOfBirth())
                        || isNotBlank(requestDto.getEmail()));
    }

    private boolean isNotBlank(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
