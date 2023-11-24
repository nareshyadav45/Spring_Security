package com.security.jdbc.auth;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private DataSource dataSource;

	@Autowired
	public void authManger(AuthenticationManagerBuilder manager) throws Exception {
		manager.jdbcAuthentication().dataSource(dataSource).passwordEncoder(new BCryptPasswordEncoder())
				.usersByUsernameQuery("select username,password,enabled from secu.users where username=? and password=?")
				.authoritiesByUsernameQuery("select username,role from secu.authorities where username=?");
	}

	@Bean
	public SecurityFilterChain securityFilter(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(req -> 
		req.antMatchers("/").permitAll()
		.antMatchers("/admin").hasRole("ADMIN")
				.antMatchers("/user").hasAnyRole("ADMIN", "USER")
				.anyRequest().authenticated()
				).formLogin();

		return http.build();

	}

}
