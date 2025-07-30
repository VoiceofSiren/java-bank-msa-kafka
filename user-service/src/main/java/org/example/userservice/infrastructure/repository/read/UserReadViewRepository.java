package org.example.userservice.infrastructure.repository.read;

import org.example.userservice.domain.model.UserReadView;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface UserReadViewRepository extends ReactiveCrudRepository<UserReadView, Long>, UserReadViewRepositoryCustom {
}
