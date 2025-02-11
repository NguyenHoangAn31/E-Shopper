package com.example.security.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.security.services.authentication.UserDetailsServiceImpl;
import com.example.security.services.user.LoginAttemptService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoginAttemptFilter extends OncePerRequestFilter {

    @Autowired
    private LoginAttemptService loginAttemptService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !("/login".equals(request.getServletPath()) && "POST".equalsIgnoreCase(request.getMethod()));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println("filter check login error and block");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        System.out.println("email: " + email + ", password: " + password);
        if(email.isBlank()|| password.isBlank()) {
            response.setStatus(HttpStatus.BAD_REQUEST.value()); // 400
            response.sendRedirect("/login?error=not-blank");
            return;
        }

        if (loginAttemptService.isBlocked(email)) {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value()); // 429
            response.sendRedirect("/login?error=too-many-requests");
            return;
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        System.out.println("userDetails: " + userDetails.isEnabled());
        if (!userDetails.isEnabled()) {
            response.setStatus(HttpStatus.LOCKED.value()); // 423
            response.sendRedirect("/login?error=blocked");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
