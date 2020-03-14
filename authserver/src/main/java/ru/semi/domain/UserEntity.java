package ru.semi.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
	private Long id;
	private String login;
	private String encodedPassword;
	private String externalId;
	// todo should be enum
	private String role;
	private boolean active;
	// TODO: 01.03.2020 must be entity
	private Long tenantId;
}
