package org.pronsky.transfer_service.service;

import org.pronsky.transfer_service.service.dto.request.CreateUserRequestDto;
import org.pronsky.transfer_service.service.dto.request.SearchUserRequestDto;
import org.pronsky.transfer_service.service.dto.request.UpdateUserRequestDto;
import org.pronsky.transfer_service.service.dto.response.PageableUserResponseDto;
import org.pronsky.transfer_service.service.dto.response.SingleUserResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    SingleUserResponseDto createUser(CreateUserRequestDto createUserRequestDto);

    SingleUserResponseDto updateUser(UpdateUserRequestDto updateUserRequestDto);

    PageableUserResponseDto findUsersFiltered(SearchUserRequestDto searchUserRequestDto);
}
