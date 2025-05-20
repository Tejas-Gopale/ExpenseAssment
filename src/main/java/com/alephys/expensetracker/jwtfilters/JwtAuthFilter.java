package com.alephys.expensetracker.jwtfilters;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.alephys.expensetracker.entity.User;
import com.alephys.expensetracker.service.CustomUserDetailsService;
import com.alephys.expensetracker.serviceImplementation.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

	private final JwtService jwtService;
	 private final CustomUserDetailsService userService;
	 private final HandlerExceptionResolver handlerExceptionResolver;
	
	 public JwtAuthFilter(JwtService jwtService, 
	                     CustomUserDetailsService userService,
	                     @Qualifier("handlerExceptionResolver") HandlerExceptionResolver handlerExceptionResolver) {
	     this.jwtService = jwtService;
	     this.userService = userService;
	     this.handlerExceptionResolver = handlerExceptionResolver;
	 }

    private static final String BEARER_PREFIX = "Bearer";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            final String requestTokenHeader = request.getHeader("Authorization");
            if (requestTokenHeader == null || !requestTokenHeader.startsWith(BEARER_PREFIX)) {
                filterChain.doFilter(request, response);
                return;
            }
            String token = requestTokenHeader.substring(BEARER_PREFIX.length()).trim();
            String email = jwtService.getEmailFromToken(token);
            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                User user = userService.getUserByEmail(email);
                if (user != null) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    log.info("User {} authenticated successfully", email);
                }
            }
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            log.error("Authentication error: {}", ex.getMessage());
            handlerExceptionResolver.resolveException(request, response, null, ex);
        }
    }
}


