package org.pronsky.transfer_service.service.mapper;

import org.mapstruct.Mapper;
import org.pronsky.transfer_service.data.entity.EmailData;
import org.pronsky.transfer_service.service.dto.EmailDataDto;

@Mapper(componentModel = "spring")
public interface EmailMapper {

    EmailDataDto emailDataEntityToEmailDataResponseDto(EmailData emailData);
}
