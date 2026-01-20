package com.backend.Entity;

import com.backend.constants.AuthenticationProvider;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AttributeOverride(name = "id", column = @Column(name = "user_id"))
@Entity
@Table(name = "user")
@ToString
@Getter
@Setter
public class User extends BaseEntity {
	private String name;
	private String  email;
	private String phone;
	private String password;
	@Enumerated(EnumType.STRING)
	@Column(name = "auth_provider", nullable = false)
	private AuthenticationProvider authProvider;
	
}

