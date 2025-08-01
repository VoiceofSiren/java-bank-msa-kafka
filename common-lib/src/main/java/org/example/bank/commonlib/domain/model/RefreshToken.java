package org.example.bank.commonlib.domain.model;

import lombok.Getter;
import org.example.apigateway.infrastructure.configuration.jwt.JwtConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.util.Date;

@Getter
@Table(name = "refresh_tokens")
public class RefreshToken implements Serializable {

    @Id
    private Long id;

    @Column
    private String username;

    @Column
    private String refresh;

    @Column
    private String expiration;

    public void rotateRefresh(String refresh) {
        this.refresh = refresh;
        this.expiration = new Date(System.currentTimeMillis() + JwtConstants.REFRESH_TOKEN_EXPIRATION).toInstant().toString();
    }
}
