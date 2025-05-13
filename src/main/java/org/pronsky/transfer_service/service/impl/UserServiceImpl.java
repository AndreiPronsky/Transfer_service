package org.pronsky.transfer_service.service.impl;

import org.pronsky.transfer_service.service.UserService;
import org.pronsky.transfer_service.service.dto.request.CreateUserRequestDto;
import org.pronsky.transfer_service.service.dto.request.SearchUserRequestDto;
import org.pronsky.transfer_service.service.dto.request.UpdateUserRequestDto;
import org.pronsky.transfer_service.service.dto.response.PageableUserResponseDto;
import org.pronsky.transfer_service.service.dto.response.SingleUserResponseDto;

public class UserServiceImpl implements UserService {

    @Override
    public SingleUserResponseDto createUser(CreateUserRequestDto createUserRequestDto) {
        return null;
    }

    @Override
    public SingleUserResponseDto updateUser(UpdateUserRequestDto updateUserRequestDto) {
        return null;
    }

    @Override
    public PageableUserResponseDto findUsersFiltered(SearchUserRequestDto searchUserRequestDto) {
        return null;
    }
}
