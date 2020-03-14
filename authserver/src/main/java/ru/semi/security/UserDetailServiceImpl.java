package ru.semi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.semi.domain.UserEntity;

import java.util.Arrays;
import java.util.List;

@Service("UserDetailServiceImpl")
public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		// hard coding the users. All passwords must be encoded.
		final List<UserEntity> users = Arrays.asList(
				new UserEntity(1L, "admin", passwordEncoder.encode("12345"), "generatedId+tenantName", "USER", true, 1L),
				new UserEntity(2L, "manager", passwordEncoder.encode("12345"),"generatedId+tenantName", "ADMIN", true, 1L)
		);

		for (UserEntity user : users) {
			if (user.getLogin().equals(login)) {
				// Remember that Spring needs roles to be in this format: "ROLE_" + userRole (i.e. "ROLE_ADMIN")
				// So, we need to set it to that format, so we can verify and compare roles (i.e. hasRole("ADMIN")).
				List<GrantedAuthority> grantedAuthorities = AuthorityUtils
						.commaSeparatedStringToAuthorityList("ROLE_" + user.getRole());

				// The "User" class is provided by Spring and represents a model class for user to be returned by UserDetailsService
				// And used by auth manager to verify and check user authentication.
				return new User(user.getLogin(), user.getEncodedPassword(), grantedAuthorities);
			}
		}

		throw new UsernameNotFoundException("User with login: " + login + " not found");

	}
}
