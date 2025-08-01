package org.example.userservice.presentation.controller.write;

import lombok.RequiredArgsConstructor;
import org.example.userservice.application.dto.UserCreateResponseDto;
import org.example.userservice.application.service.UserService;
import org.example.userservice.presentation.request.UserCreateRequestDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/write/users")
public class UserWriteController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public Mono<UserCreateResponseDto> signUp(
            @RequestBody UserCreateRequestDto userCreateRequestDto
            ) {
        return userService.createUser(userCreateRequestDto);
    }
}
