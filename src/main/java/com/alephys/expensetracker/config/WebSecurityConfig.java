package com.alephys.expensetracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.alephys.expensetracker.jwtfilters.JwtAuthFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity 
@RequiredArgsConstructor
public class WebSecurityConfig {

	private final JwtAuthFilter authFilter;
		
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
			.authorizeHttpRequests(auth -> 
					auth
					.requestMatchers("/auth/v1/**","/api/expenses/**","/v3/api-docs","/v2/api-docs", "/swagger-ui/**", "/swagger-resources/**", "/webjars/**")
					.permitAll()
					.anyRequest().authenticated())
					.csrf(csrfconfig -> csrfconfig.disable())
					.sessionManagement(sessionConfig  -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
					.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);					
		return httpSecurity.build();
	}
	
	@Bean
	AuthenticationManager authentactionManager (AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
	
	    @Bean
	    public PasswordEncoder passwordEncoder() {
	        return NoOpPasswordEncoder.getInstance(); // Use NoOpPasswordEncoder for plaintext passwords
	    }
}
