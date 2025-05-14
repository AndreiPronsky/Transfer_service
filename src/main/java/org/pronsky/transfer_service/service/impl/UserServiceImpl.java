package org.pronsky.transfer_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.pronsky.transfer_service.data.repository.AccountRepository;
import org.pronsky.transfer_service.data.repository.EmailDataRepository;
import org.pronsky.transfer_service.data.repository.PhoneDataRepository;
import org.pronsky.transfer_service.data.repository.UserRepository;
import org.pronsky.transfer_service.service.UserService;
import org.pronsky.transfer_service.service.dto.request.SearchUserRequestDto;
import org.pronsky.transfer_service.service.dto.response.EmailDataResponseDto;
import org.pronsky.transfer_service.service.dto.response.PageableResponseDto;
import org.pronsky.transfer_service.service.dto.response.PhoneDataResponseDto;
import org.pronsky.transfer_service.service.dto.response.SingleUserResponseDto;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PhoneDataRepository phoneDataRepository;
    private final EmailDataRepository emailDataRepository;
    private final AccountRepository accountRepository;

    @Override
    public void addEmailToUser(Long userId, String email) {


    }

    @Override
    public void addPhoneNumberToUser(Long userId, String phoneNumber) {

    }

    @Override
    public void updateEmail(Long userId, Long emailId, String email) {

    }

    @Override
    public void updatePhoneNumber(Long userId, Long phoneNumberId, String phoneNumber) {

    }

    @Override
    public void deleteEmailFromUser(Long userId, Long emailId) {

    }

    @Override
    public void deletePhoneNumberFromUser(Long userId, Long phoneNumberId) {

    }

    @Override
    public PageableResponseDto<SingleUserResponseDto> searchUsers(Pageable pageable, SearchUserRequestDto searchDto) {
        return null;
    }

    @Override
    public PhoneDataResponseDto getPhoneNumbersByUserId(Long userId) {
        return null;
    }

    @Override
    public EmailDataResponseDto getEmailsByUserId(Long userId) {
        return null;
    }
}
