package org.pronsky.transfer_service.service.impl;

import org.pronsky.transfer_service.service.UserService;
import org.pronsky.transfer_service.service.dto.response.EmailDataResponseDto;
import org.pronsky.transfer_service.service.dto.response.PageableResponseDto;
import org.pronsky.transfer_service.service.dto.response.PhoneDataResponseDto;
import org.pronsky.transfer_service.service.dto.response.SingleUserResponseDto;
import org.springframework.data.domain.Pageable;

public class UserServiceImpl implements UserService {

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
    public void updatePhoneNumberByUserId(Long userId, Long phoneNumberId, String phoneNumber) {

    }

    @Override
    public void deleteEmailFromUser(Long userId, Long emailId) {

    }

    @Override
    public void deletePhoneNumberFromUser(Long userId, Long phoneNumberId) {

    }

    @Override
    public PageableResponseDto<SingleUserResponseDto> getUsersFiltered(Pageable pageable, String query) {
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
