package org.example.userservice.infrastructure.repository.read;

import org.example.userservice.domain.model.UserReadView;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;

public interface UserReadViewRepositoryCustom {
    Flux<UserReadView> findAllUsersLimitedByPageable(Pageable pageable);
}
