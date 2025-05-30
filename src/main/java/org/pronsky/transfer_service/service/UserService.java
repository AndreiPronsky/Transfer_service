package org.pronsky.transfer_service.service;

import org.pronsky.transfer_service.service.dto.EmailDataDto;
import org.pronsky.transfer_service.service.dto.PhoneDataDto;
import org.pronsky.transfer_service.service.dto.request.SearchUserRequestDto;
import org.pronsky.transfer_service.service.dto.request.UpdateEmailRequestDto;
import org.pronsky.transfer_service.service.dto.request.UpdatePhoneRequestDto;
import org.pronsky.transfer_service.service.dto.response.PageableResponseDto;
import org.pronsky.transfer_service.service.dto.response.SingleUserResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface UserService {

    void addEmailToUser(Long userId, String email);

    void addPhoneNumberToUser(Long userId, String phoneNumber);

    void updateEmail(UpdateEmailRequestDto updateEmailRequestDto);

    void updatePhoneNumber(UpdatePhoneRequestDto updatePhoneRequestDto);

    void deleteEmail(Long userId, Long emailId);

    void deletePhoneNumberFromUser(Long userId, Long phoneNumberId);

    Set<PhoneDataDto> getPhoneNumbersByUserId(Long userId);

    Set<EmailDataDto> getEmailsByUserId(Long userId);

    PageableResponseDto<SingleUserResponseDto> searchUsers(Pageable pageable, SearchUserRequestDto searchDto);
}
