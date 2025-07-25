package org.example.apigateway.domain.model;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

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
}
