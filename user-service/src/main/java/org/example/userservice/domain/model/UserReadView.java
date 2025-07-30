package org.example.userservice.domain.model;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Table(name = "user_read_views")
public class UserReadView {

    @Id
    private Long id;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String role;
}
