package org.pronsky.transfer_service.service.mapper;

import org.mapstruct.Mapper;
import org.pronsky.transfer_service.data.entity.User;
import org.pronsky.transfer_service.service.dto.response.SingleUserResponseDto;

@Mapper(componentModel = "spring")
public interface UserMapper {

    SingleUserResponseDto entityToUserDataDto(User user);

}
