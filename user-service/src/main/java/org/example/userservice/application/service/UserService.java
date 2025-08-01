package org.example.userservice.application.service;

import lombok.RequiredArgsConstructor;
import org.example.userservice.application.dto.UserCreateResponseDto;
import org.example.userservice.domain.model.UserReadView;
import org.example.userservice.infrastructure.repository.read.UserReadViewRepository;
import org.example.userservice.presentation.request.UserCreateRequestDto;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserReadViewRepository userReadViewRepository;

    public Flux<UserReadView> getAllUsers(Pageable pageable) {
        return userReadViewRepository.findAllUsersLimitedByPageable(pageable);
    }

    public Mono<UserCreateResponseDto> createUser(UserCreateRequestDto userCreateRequestDto) {
        // TODO
        return Mono.empty();
    }
}
