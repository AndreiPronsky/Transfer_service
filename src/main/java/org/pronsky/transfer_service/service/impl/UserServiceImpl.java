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
import org.pronsky.transfer_service.service.dto.request.UpdateEmailRequestDto;
import org.pronsky.transfer_service.service.dto.request.UpdatePhoneRequestDto;
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
import java.util.stream.Collectors;

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
    private static final String EMAIL_DOES_NOT_BELONG_TO_USER = "Email %s does not belong to user %s";
    private static final String PHONE_DOES_NOT_BELONG_TO_USER = "Phone number %s does not belong to user %s";
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
        EmailData emailData = EmailData.builder()
                .user(user)
                .email(email)
                .build();
        emailDataRepository.save(emailData);
        log.info(EMAIL_ADDED_TO_USER, email, userId);
    }


    @Override
    @Transactional
    public void addPhoneNumberToUser(Long userId, String phoneNumber) {
        User user = getUserById(userId);
        PhoneData phoneData = (PhoneData.builder()
                .user(user)
                .phoneNumber(phoneNumber)
                .build());
        phoneDataRepository.save(phoneData);
        log.info(PHONE_NUMBER_ADDED_TO_USER, phoneNumber, userId);
    }

    @Override
    @Transactional
    public void updatePhoneNumber(UpdatePhoneRequestDto dto) {
        Long phoneId = dto.getPhoneId();
        Long userId = dto.getUserId();
        User user = getUserById(userId);
        PhoneData phoneData = phoneDataRepository.findById(phoneId)
                .orElseThrow(EntityNotFoundException::new);
        if (!user.equals(phoneData.getUser())) {
            throw new SecurityException(
                    String.format(PHONE_DOES_NOT_BELONG_TO_USER, phoneId, userId));
        }
        phoneData.setPhoneNumber(dto.getNewPhoneNumber());
        phoneDataRepository.save(phoneData);
        log.info(PHONE_NUMBER_UPDATED_FOR_USER, dto.getNewPhoneNumber(), userId);
    }

    @Override
    @Transactional
    public void updateEmail(UpdateEmailRequestDto dto) {
        Long emailId = dto.getEmailId();
        Long userId = dto.getUserId();
        User user = getUserById(userId);
        EmailData emailData = emailDataRepository.findById(emailId)
                .orElseThrow(EntityNotFoundException::new);
        if (!user.equals(emailData.getUser())) {
            throw new SecurityException(
                    String.format(EMAIL_DOES_NOT_BELONG_TO_USER, emailId, userId));
        }
        emailData.setEmail(dto.getNewEmail());
        emailDataRepository.save(emailData);
        log.info(EMAIL_UPDATED_FOR_USER, dto.getNewEmail(), dto.getUserId());
    }

    @Override
    @Transactional
    public void deleteEmail(Long userId, Long emailId) {
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

        LocalDate dateOfBirth = searchDto.getDateOfBirth() == null ? null : parseDate(searchDto.getDateOfBirth());

        Specification<User> specification = hasDateOfBirthAfter(dateOfBirth)
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
    public Set<PhoneDataDto> getPhoneNumbersByUserId(Long userId) {
        return phoneDataRepository.findAllPhoneDataByUserId(userId).stream()
                .map(phoneMapper::phoneDataEntityToPhoneDataDto)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<EmailDataDto> getEmailsByUserId(Long userId) {
        return emailDataRepository.findAllEmailDataByUserId(userId).stream()
                .map(emailMapper::emailDataEntityToEmailDataResponseDto)
                .collect(Collectors.toSet());
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
