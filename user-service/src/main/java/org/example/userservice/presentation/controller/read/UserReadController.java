package org.example.userservice.presentation.controller.read;

import lombok.RequiredArgsConstructor;
import org.example.userservice.application.service.UserService;
import org.example.userservice.domain.model.UserReadView;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/read/users")
public class UserReadController {

    private final UserService userService;

    @GetMapping
    public Flux<UserReadView> findAllUsers(@PageableDefault(size = 10) Pageable pageable) {
        return userService.getAllUsers(pageable);
    }

}
