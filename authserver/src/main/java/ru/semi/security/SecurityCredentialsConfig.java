package ru.semi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.semi.filter.JwtUsernameAndPasswordAuthenticationFilter;
import ru.semi.utils.JwtConfig;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
public class SecurityCredentialsConfig extends WebSecurityConfigurerAdapter {

	@Qualifier("UserDetailServiceImpl")
	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtConfig jwtConfig;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
					.csrf().disable()
					// не храним сессии
					.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
					.exceptionHandling().authenticationEntryPoint((req, res, e) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED))
				.and()
					//
					.addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), jwtConfig))
				.authorizeRequests()
					.antMatchers(HttpMethod.POST, jwtConfig.getUri()).permitAll()
					.anyRequest().authenticated();

	}


	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public JwtConfig jwtConfig() {
		return new JwtConfig();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder () {
		return new BCryptPasswordEncoder();
	}
}
