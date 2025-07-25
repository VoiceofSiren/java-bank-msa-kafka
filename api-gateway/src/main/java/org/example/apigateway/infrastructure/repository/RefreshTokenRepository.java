package org.example.apigateway.infrastructure.repository;

import org.example.apigateway.domain.model.RefreshToken;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface RefreshTokenRepository extends ReactiveCrudRepository<RefreshToken, Long> {
    Mono<RefreshToken> findByUsername(String username);
}
