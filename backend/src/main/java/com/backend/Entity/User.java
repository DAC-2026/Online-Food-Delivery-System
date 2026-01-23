package com.backend.Entity;

import com.backend.constants.AuthenticationProvider;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AttributeOverride(name = "id", column = @Column(name = "user_id"))
@Entity
@Table(name = "users") // "user" is reserved in many DBs
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "password")
public class User extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(nullable = false, length = 10)
    private String phone;

    @Column(nullable = false, length = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "auth_provider", nullable = false)
    private AuthenticationProvider authProvider;
}
