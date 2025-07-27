package org.example.apigateway.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.example.apigateway.domain.model.RefreshToken;
import org.example.apigateway.infrastructure.configuration.jwt.JwtConstants;
import org.example.apigateway.infrastructure.configuration.jwt.JwtUtils;
import org.example.apigateway.infrastructure.repository.RefreshTokenRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/gateway/auth")
public class AuthController {

    private final JwtUtils jwtUtils;
    private final RefreshTokenRepository refreshTokenRepository;

    @PostMapping("/refresh")
    public Mono<ResponseEntity<?>> refresh(ServerWebExchange exchange) {
        // [1] Cookie에서 refreshToken 꺼내기
        String refreshToken = exchange.getRequest().getCookies().getFirst("refreshToken").getValue();

        if (refreshToken == null || !jwtUtils.isValid(refreshToken)) {
            return Mono.just(ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid refresh token"));
        }

        String username = jwtUtils.getUsernameFrom(refreshToken);

        Mono<RefreshToken> refreshTokenMono = refreshTokenRepository.findByUsername(username);

        return refreshTokenMono.flatMap(rt -> {
            if (!rt.getRefresh().equals(refreshToken)) {
                return Mono.just(ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body("Replay attack detected"));
            }

            // [2] 신규 토큰 발급
            String newAccessToken = jwtUtils.issueJwt("access", username, "USER", JwtConstants.ACCESS_TOKEN_EXPIRATION);
            String newRefreshToken = jwtUtils.issueJwt("refresh", username, "USER", JwtConstants.REFRESH_TOKEN_EXPIRATION);

            // [3] Refresh token 교체
            rt.rotateRefresh(newRefreshToken);

            return refreshTokenRepository.save(rt)
                    .map(updated -> {
                        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", newRefreshToken)
                                .httpOnly(true)
                                .path("/")
                                .maxAge(JwtConstants.REFRESH_TOKEN_EXPIRATION)
                                .build();

                        return ResponseEntity.ok()
                                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + newAccessToken)
                                .body("New tokens issued");
                    });

        });
    }
}
