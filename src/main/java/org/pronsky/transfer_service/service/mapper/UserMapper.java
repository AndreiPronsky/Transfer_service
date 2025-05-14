package org.pronsky.transfer_service.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.pronsky.transfer_service.data.entity.Account;
import org.pronsky.transfer_service.data.entity.EmailData;
import org.pronsky.transfer_service.data.entity.PhoneData;
import org.pronsky.transfer_service.data.entity.User;
import org.pronsky.transfer_service.service.dto.request.CreateUserRequestDto;
import org.pronsky.transfer_service.service.dto.request.UpdateUserRequestDto;
import org.pronsky.transfer_service.service.dto.response.SingleUserResponseDto;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "name", source = "name")
    @Mapping(target = "dateOfBirth", expression = "java(java.time.LocalDate.parse(dto.getDateOfBirth()))")
    @Mapping(target = "password", source = "password")
    User fromCreateUserRequestDtoToUser(CreateUserRequestDto createUserRequestDto);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "actualBalance", expression = "java(dto.getInitialBalance())")
    Account toAccount(CreateUserRequestDto dto);

    @Mapping(target = "user", ignore = true)
    EmailData toEmailData(CreateUserRequestDto dto);

    @Mapping(target = "user", ignore = true)
    PhoneData toPhoneData(CreateUserRequestDto dto);

    SingleUserResponseDto fromUserToSingleUserResponseDto(User user);
}
