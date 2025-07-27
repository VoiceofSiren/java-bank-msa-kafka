package org.example.bank.commonlib.infrastructure.configuration.jwt;

import lombok.RequiredArgsConstructor;
import org.example.apigateway.infrastructure.configuration.jwt.JwtAuthenticationManager;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class JwtSecurityContextRepository implements ServerSecurityContextRepository {

    private final JwtAuthenticationManager jwtAuthenticationManager;
    private final JwtUtils jwtUtils;

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        return Mono.empty();    // stateless
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // "Bearer " 제거

            // access token만 인증에 사용
            if (jwtUtils.isValid(token)) {
                String category = jwtUtils.getCategoryFrom(token);

                // category가 "access"인 경우만 인증 수행
                if ("access".equalsIgnoreCase(category)) {
                    Authentication auth = new UsernamePasswordAuthenticationToken(token, token);
                    return jwtAuthenticationManager.authenticate(auth).map(SecurityContextImpl::new);
                }
            }
        }

        return Mono.empty();
    }
}
