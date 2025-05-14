package org.pronsky.transfer_service.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pronsky.transfer_service.service.UserService;
import org.pronsky.transfer_service.service.dto.request.AddEmailToUserRequestDto;
import org.pronsky.transfer_service.service.dto.request.AddPhoneToUserRequestDto;
import org.pronsky.transfer_service.service.dto.request.UpdateEmailRequestDto;
import org.pronsky.transfer_service.service.dto.request.UpdatePhoneRequestDto;
import org.pronsky.transfer_service.service.dto.response.PageableResponseDto;
import org.pronsky.transfer_service.service.dto.response.PhoneDataResponseDto;
import org.pronsky.transfer_service.service.dto.response.SingleUserResponseDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@Validated
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/search")
    public ResponseEntity<PageableResponseDto<SingleUserResponseDto>> search(
            @RequestParam(value = "q") String query,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "5") int size
    ) {
        return ResponseEntity.ok(userService.getUsersFiltered(PageRequest.of(page - 1, size), query));
    }

    @GetMapping("show-phones/{userId}")
    public ResponseEntity<PhoneDataResponseDto> getUserPhones(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(userService.getPhoneNumbersByUserId(userId));
    }

    @GetMapping("show-emails/{userId}")
    public ResponseEntity<PhoneDataResponseDto> getUserEmails(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(userService.getPhoneNumbersByUserId(userId));
    }

    @PostMapping("/add-phone")
    public ResponseEntity<Void> addPhoneToUserById(@RequestBody @Valid AddPhoneToUserRequestDto requestDto) {
        userService.addPhoneNumberToUser(requestDto.getUserId(), requestDto.getPhoneNumber());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/add-email")
    public ResponseEntity<Void> addEmailToUserById(@RequestBody @Valid AddEmailToUserRequestDto requestDto) {
        userService.addEmailToUser(requestDto.getUserId(), requestDto.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/edit-email")
    public ResponseEntity<Void> updateEmail(@RequestBody @Valid UpdateEmailRequestDto requestDto) {
        userService.updateEmail(requestDto.getUserId(), requestDto.getEmailId(), requestDto.getNewEmail());
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PatchMapping("/edit-phone")
    public ResponseEntity<Void> updatePhone(@RequestBody @Valid UpdatePhoneRequestDto requestDto) {
        userService.updateEmail(requestDto.getUserId(), requestDto.getPhoneId(), requestDto.getNewPhoneNumber());
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @DeleteMapping("/{userId}/delete-phone/{phoneId}")
    public ResponseEntity<Void> deletePhoneByIdByUserId(
            @PathVariable("userId") Long userId,
            @PathVariable("phoneId") Long phoneId
    ) {
        userService.deletePhoneNumberFromUser(userId, phoneId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{userId}/delete-email/{emailId}")
    public ResponseEntity<Void> deleteEmailByIdByUserId(
            @PathVariable("userId") Long userId,
            @PathVariable("emailId") Long emailId
    ) {
        userService.deleteEmailFromUser(userId, emailId);
        return ResponseEntity.noContent().build();
    }
}
