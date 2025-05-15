package org.pronsky.transfer_service.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pronsky.transfer_service.data.entity.EmailData;
import org.pronsky.transfer_service.data.entity.PhoneData;
import org.pronsky.transfer_service.data.entity.User;
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
import org.pronsky.transfer_service.service.mapper.UserMapper;
import org.pronsky.transfer_service.util.PageableResponseUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Set;

import static org.pronsky.transfer_service.service.specification.UserSpecification.hasDateOfBirthAfter;
import static org.pronsky.transfer_service.service.specification.UserSpecification.hasEmail;
import static org.pronsky.transfer_service.service.specification.UserSpecification.hasPhoneNumber;
import static org.pronsky.transfer_service.service.specification.UserSpecification.nameLike;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private static final String USER_NOT_FOUND = "User with id %s not found";
    private static final String EMAIL_ADDED_TO_USER = "Email {} added to user {}";
    private static final String PHONE_NUMBER_ADDED_TO_USER = "Phone {} added to user {}";
    private static final String EMAIL_UPDATED_FOR_USER = "Email {} updated for user {}";
    private static final String PHONE_NUMBER_UPDATED_FOR_USER = "Phone {} updated for user {}";
    private static final String DELETE_EMAIL_FROM_USER = "Email {} deleted for user {}";
    private static final String DELETE_PHONE_NUMBER_FROM_USER = "Phone {} deleted for user {}";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private final UserRepository userRepository;
    private final PhoneDataRepository phoneDataRepository;
    private final EmailDataRepository emailDataRepository;
    private final EmailMapper emailMapper;
    private final PhoneMapper phoneMapper;
    private final UserMapper userMapper;
    private final PageableResponseUtil pageableResponseUtil;

    @Override
    @Transactional
    public void addEmailToUser(Long userId, String email) {
        User user = getUserById(userId);
        Set<EmailData> userEmails = emailDataRepository.findAllEmailDataByUserId(userId);
        userEmails.add(EmailData.builder()
                .user(user)
                .email(email)
                .build());
        log.info(EMAIL_ADDED_TO_USER, email, userId);
    }


    @Override
    @Transactional
    public void addPhoneNumberToUser(Long userId, String phoneNumber) {
        User user = getUserById(userId);
        Set<PhoneData> userPhones = phoneDataRepository.findAllPhoneDataByUserId(userId);
        userPhones.add(PhoneData.builder()
                .user(user)
                .phone(phoneNumber)
                .build());
        log.info(PHONE_NUMBER_ADDED_TO_USER, phoneNumber, userId);
    }

    @Override
    @Transactional
    public void updatePhoneNumber(Long userId, Long phoneId, String phoneNumber) {
        User user = getUserById(userId);
        Set<PhoneData> userPhones = phoneDataRepository.findAllPhoneDataByUserId(phoneId);
        userPhones.add(PhoneData.builder()
                .user(user)
                .phone(phoneNumber)
                .build());
        log.info(PHONE_NUMBER_UPDATED_FOR_USER, phoneNumber, userId);
    }

    @Override
    @Transactional
    public void updateEmail(Long userId, Long emailId, String email) {
        User user = getUserById(userId);
        Set<EmailData> userEmails = emailDataRepository.findAllEmailDataByUserId(emailId);
        userEmails.add(EmailData.builder()
                .user(user)
                .email(email)
                .build());
        log.info(EMAIL_UPDATED_FOR_USER, email, userId);
    }

    @Override
    @Transactional
    public void deleteEmailFromUser(Long userId, Long emailId) {
        log.info(DELETE_EMAIL_FROM_USER, emailId, userId);
        emailDataRepository.deleteByIdAndUserId(emailId, userId);
    }

    @Override
    @Transactional
    public void deletePhoneNumberFromUser(Long userId, Long phoneNumberId) {
        log.info(DELETE_PHONE_NUMBER_FROM_USER, phoneNumberId, userId);
        phoneDataRepository.deleteByIdAndUserId(phoneNumberId, userId);
    }

    @Override
    public PageableResponseDto<SingleUserResponseDto> searchUsers(Pageable pageable, SearchUserRequestDto searchDto) {

        Specification<User> specification = hasDateOfBirthAfter(parseDate(searchDto.getDateOfBirth()))
                .and(hasEmail(searchDto.getEmail()))
                .and(hasPhoneNumber(searchDto.getPhone()))
                .and(nameLike(searchDto.getName()));

        Page<User> users = userRepository.findAll(specification, pageable);

        List<SingleUserResponseDto> content = users.stream()
                .map(userMapper::entityToSingleUserResponseDto)
                .toList();
        return pageableResponseUtil.buildPageableResponse(content, users, new PageableResponseDto<>());
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

    private LocalDate parseDate(String date) {
        try {
            return LocalDate.parse(date, FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format, expected dd.MM.yyyy", e);
        }
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(String.format(USER_NOT_FOUND, userId)));
    }
}
