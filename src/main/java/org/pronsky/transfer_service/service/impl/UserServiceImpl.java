package org.pronsky.transfer_service.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.pronsky.transfer_service.data.entity.EmailData;
import org.pronsky.transfer_service.data.entity.PhoneData;
import org.pronsky.transfer_service.data.entity.User;
import org.pronsky.transfer_service.data.repository.AccountRepository;
import org.pronsky.transfer_service.data.repository.EmailDataRepository;
import org.pronsky.transfer_service.data.repository.PhoneDataRepository;
import org.pronsky.transfer_service.data.repository.UserRepository;
import org.pronsky.transfer_service.service.UserService;
import org.pronsky.transfer_service.service.dto.EmailDataDto;
import org.pronsky.transfer_service.service.dto.PhoneDataDto;
import org.pronsky.transfer_service.service.dto.request.SearchUserRequestDto;
import org.pronsky.transfer_service.service.dto.response.PageableResponseDto;
import org.pronsky.transfer_service.service.dto.response.SingleUserResponseDto;
import org.pronsky.transfer_service.service.mapper.EmailMapper;
import org.pronsky.transfer_service.service.mapper.PhoneMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    public static final String NOT_FOUND = " not found";
    public static final String USER_WITH_ID = "User with id ";
    private final UserRepository userRepository;
    private final PhoneDataRepository phoneDataRepository;
    private final EmailDataRepository emailDataRepository;
    private final AccountRepository accountRepository;
    private final EmailMapper emailMapper;
    private final PhoneMapper phoneMapper;

    @Override
    public void addEmailToUser(Long userId, String email) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(USER_WITH_ID + userId + NOT_FOUND));
        Set<EmailData> userEmails = emailDataRepository.findAllEmailDataByUserId(userId);
        userEmails.add(EmailData.builder()
                .user(user)
                .email(email)
                .build());
    }

    @Override
    public void addPhoneNumberToUser(Long userId, String phoneNumber) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(USER_WITH_ID + userId + NOT_FOUND));
        Set<PhoneData> userPhones = phoneDataRepository.findAllPhoneDataByUserId(userId);
        userPhones.add(PhoneData.builder()
                .user(user)
                .phone(phoneNumber)
                .build());
    }

    @Override
    public void updateEmail(Long userId, Long emailId, String email) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(USER_WITH_ID + userId + NOT_FOUND));
        Set<EmailData> userEmails = emailDataRepository.findAllEmailDataByUserId(emailId);
        userEmails.add(EmailData.builder()
                .user(user)
                .email(email)
                .build());
    }

    @Override
    public void updatePhoneNumber(Long userId, Long phoneNumberId, String phoneNumber) {
        PhoneData phoneData = phoneDataRepository.findByIdAndUserId(phoneNumberId, userId);
        phoneData.setPhone(phoneNumber);
    }

    @Override
    public void deleteEmailFromUser(Long userId, Long emailId) {
        emailDataRepository.deleteByIdAndUserId(emailId, userId);
    }

    @Override
    public void deletePhoneNumberFromUser(Long userId, Long phoneNumberId) {
        phoneDataRepository.deleteByIdAndUserId(phoneNumberId, userId);
    }

    @Override
    public PageableResponseDto<SingleUserResponseDto> searchUsers(Pageable pageable, SearchUserRequestDto searchDto) {
        return null;
    }

    @Override
    public List<PhoneDataDto> getPhoneNumbersByUserId(Long userId) {
        return phoneDataRepository.findAllPhoneDataByUserId(userId).stream()
                .map(phoneMapper::phoneDataEntityToPhoneDataResponseDto)
                .toList();
    }

    @Override
    public List<EmailDataDto> getEmailsByUserId(Long userId) {
        return emailDataRepository.findAllEmailDataByUserId(userId).stream()
                .map(emailMapper::emailDataEntityToEmailDataResponseDto)
                .toList();
    }
}
