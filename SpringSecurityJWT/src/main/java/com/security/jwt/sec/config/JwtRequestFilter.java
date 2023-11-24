package com.security.jwt.sec.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private MyUserDeatailService userDatils;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String jwt = null;
		String username = null;

		String authorizationHeaderToken = request.getHeader("Authorization");

		if (authorizationHeaderToken != null && authorizationHeaderToken.startsWith("Bearer ")) {
			jwt = authorizationHeaderToken.substring(7);
			username = jwtUtil.extractUsername(jwt);
		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDeatails = this.userDatils.loadUserByUsername(username);
			
			if (jwtUtil.validateToken(jwt, userDeatails)) {
				UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDeatails,null,
						userDeatails.getAuthorities());
				token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(token);

			}
			
		}
		
		filterChain.doFilter(request, response);
		
	}

}
