package org.pronsky.transfer_service.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pronsky.transfer_service.service.UserService;
import org.pronsky.transfer_service.service.dto.EmailDataDto;
import org.pronsky.transfer_service.service.dto.PhoneDataDto;
import org.pronsky.transfer_service.service.dto.request.AddEmailToUserRequestDto;
import org.pronsky.transfer_service.service.dto.request.AddPhoneToUserRequestDto;
import org.pronsky.transfer_service.service.dto.request.SearchUserRequestDto;
import org.pronsky.transfer_service.service.dto.request.UpdateEmailRequestDto;
import org.pronsky.transfer_service.service.dto.request.UpdatePhoneRequestDto;
import org.pronsky.transfer_service.service.dto.response.ExceptionResponseDto;
import org.pronsky.transfer_service.service.dto.response.PageableResponseDto;
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

import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@Validated
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Search for users",
            description = """
                    Search for users according to a provided parameters and returns the result with pagination option.
                    At least one parameter is required.
                    """,
            parameters = {
                    @Parameter(name = "page", description = "Page number, starts from 1", example = "1"),
                    @Parameter(name = "size", description = "Number of elements on the page", example = "10"),
                    @Parameter(name = "dateOfBirth", description = "Parameter to search users by date of birth " +
                            "(greater than provided"),
                    @Parameter(name = "phone", description = "Parameter to search users by phone number (100% match)"),
                    @Parameter(name = "name", description = "Parameter to search users by name (partial match)"),
                    @Parameter(name = "email", description = "Parameter to search users by email (100% match)")
            }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users successfully retrieved",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PageableResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponseDto.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponseDto.class))),
    })
    @GetMapping("/search")
    public ResponseEntity<PageableResponseDto<SingleUserResponseDto>> search(
            @RequestParam(value = "dateOfBirth", required = false) String dateOfBirth,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "5") int size
    ) {
        SearchUserRequestDto requestDto = SearchUserRequestDto.builder()
                .dateOfBirth(dateOfBirth)
                .phone(phone)
                .name(name)
                .email(email)
                .build();
        return ResponseEntity.ok(userService.searchUsers(PageRequest.of(page - 1, size), requestDto));
    }

    @Operation(summary = "Get user phone numbers by user ID",
            description = """
                    Get phone numbers by user ID
                    """,
            parameters = {
                    @Parameter(name = "userId", description = "User ID to fetch phone numbers", example = "1")
            })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Phone numbers successfully retrieved",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PageableResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponseDto.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "User Not Found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponseDto.class))),
    })
    @GetMapping("/phones/{userId}")
    public ResponseEntity<Set<PhoneDataDto>> getUserPhones(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(userService.getPhoneNumbersByUserId(userId));
    }

    @Operation(summary = "Get user email by user ID",
            description = """
                    Get phone numbers by user ID
                    """,
            parameters = {
                    @Parameter(name = "userId", description = "User ID to fetch emails", example = "1")
            })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Emails successfully retrieved",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PageableResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponseDto.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "User Not Found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponseDto.class))),
    })
    @GetMapping("/emails/{userId}")
    public ResponseEntity<Set<EmailDataDto>> getUserEmails(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(userService.getEmailsByUserId(userId));
    }

    @Operation(
            summary = "Add phone number to the user",
            description = """
                    Adds a new phone number for the specified user.
                    Accepts update data, including the user ID and phone number.
                    """,
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AddPhoneToUserRequestDto.class))),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Phone number successfully added",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema())
                    ),
                    @ApiResponse(responseCode = "400", description = "Bad Request",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "Forbidden",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "User Not Found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponseDto.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponseDto.class))
                    )
            }
    )
    @PostMapping("/add-phone")
    public ResponseEntity<Void> addPhoneToUserById(@RequestBody @Valid AddPhoneToUserRequestDto requestDto) {
        userService.addPhoneNumberToUser(requestDto.getUserId(), requestDto.getPhoneNumber());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
            summary = "Add email to the user",
            description = """
                    Adds a new email for the specified user.
                    Accepts update data, including the user ID and email.
                    """,
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AddEmailToUserRequestDto.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Email successfully added",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema())),
                    @ApiResponse(responseCode = "400", description = "Bad Request",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "Forbidden",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "User Not Found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponseDto.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponseDto.class)))
            }
    )
    @PostMapping("/add-email")
    public ResponseEntity<Void> addEmailToUserById(@RequestBody @Valid AddEmailToUserRequestDto requestDto) {
        userService.addEmailToUser(requestDto.getUserId(), requestDto.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
            summary = "Edit email by user ID and email ID",
            description = """
                    Edits the specified email for the specified user.
                    Accepts update data, including the user ID and email ID.
                    """,
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UpdateEmailRequestDto.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "202", description = "Email successfully updated",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema())),
                    @ApiResponse(responseCode = "400", description = "Bad Request",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "Forbidden",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "User Not Found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponseDto.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponseDto.class))
                    )
            }
    )
    @PatchMapping("/edit-email")
    public ResponseEntity<Void> updateEmail(@RequestBody @Valid UpdateEmailRequestDto requestDto) {
        userService.updateEmail(requestDto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @Operation(
            summary = "Edit phone number by user ID and email ID",
            description = """
                    Edits the specified phone number for the specified user.
                    Accepts update data, including the user ID and phone number ID
                    """,
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UpdatePhoneRequestDto.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "202", description = "Phone number successfully updated",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema())),
                    @ApiResponse(responseCode = "400", description = "Bad Request",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "Forbidden",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "User Not Found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponseDto.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponseDto.class))
                    )
            }
    )
    @PatchMapping("/edit-phone")
    public ResponseEntity<Void> updatePhone(@RequestBody @Valid UpdatePhoneRequestDto requestDto) {
        userService.updatePhoneNumber(requestDto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @Operation(
            summary = "Delete phone number by user ID and phone ID",
            description = """
                    Deletes the specified phone number for the specified user.
                    Accepts update data, including the user ID and phone number ID.
                    """,
            parameters = {
                    @Parameter(name = "userId", description = "User ID to delete phone from", example = "1"),
                    @Parameter(name = "emailId", description = "Phone ID to delete", example = "1")
            },
            responses = {
                    @ApiResponse(responseCode = "204", description = "Phone number successfully deleted",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema())),
                    @ApiResponse(responseCode = "400", description = "Bad Request",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "Forbidden",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "User Not Found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponseDto.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponseDto.class))
                    )
            }
    )
    @DeleteMapping("/{userId}/delete-phone/{phoneId}")
    public ResponseEntity<Void> deletePhoneByIdAndUserId(
            @PathVariable("userId") Long userId,
            @PathVariable("phoneId") Long phoneId
    ) {
        userService.deletePhoneNumberFromUser(userId, phoneId);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Delete phone number by user ID and phone ID",
            description = """
                    Deletes the specified email for the specified user.
                    Accepts update data, including the user ID and email ID
                    """,
            parameters = {
                    @Parameter(name = "userId", description = "User ID to delete email from", example = "1"),
                    @Parameter(name = "emailId", description = "Email ID to delete", example = "1")
            },
            responses = {
                    @ApiResponse(responseCode = "204", description = "Email successfully deleted",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema())),
                    @ApiResponse(responseCode = "400", description = "Bad Request",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "Forbidden",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "User Not Found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponseDto.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponseDto.class))
                    )
            }
    )
    @DeleteMapping("{userId}/delete-email/{emailId}")
    public ResponseEntity<Void> deleteEmailByIdAndUserId(
            @PathVariable("userId") Long userId,
            @PathVariable("emailId") Long emailId
    ) {
        userService.deleteEmail(userId, emailId);
        return ResponseEntity.noContent().build();
    }
}
