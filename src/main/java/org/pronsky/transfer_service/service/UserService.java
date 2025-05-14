package org.pronsky.transfer_service.service;

import org.pronsky.transfer_service.service.dto.response.EmailDataResponseDto;
import org.pronsky.transfer_service.service.dto.response.PageableResponseDto;
import org.pronsky.transfer_service.service.dto.response.PhoneDataResponseDto;
import org.pronsky.transfer_service.service.dto.response.SingleUserResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    void addEmailToUser(Long userId, String email);

    void addPhoneNumberToUser(Long userId, String phoneNumber);

    void updateEmail(Long userId, Long emailId, String email);

    void updatePhoneNumberByUserId(Long userId, Long phoneNumberId, String phoneNumber);

    void deleteEmailFromUser(Long userId, Long emailId);

    void deletePhoneNumberFromUser(Long userId, Long phoneNumberId);

    PhoneDataResponseDto getPhoneNumbersByUserId(Long userId);

    EmailDataResponseDto getEmailsByUserId(Long userId);

    PageableResponseDto<SingleUserResponseDto> getUsersFiltered(Pageable pageable, String query);
}
