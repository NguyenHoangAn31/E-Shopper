package com.example.security.filter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {


        String errorMessage = null;

        if (exception instanceof DisabledException) {
            errorMessage = "disabled";
        } else if (exception instanceof BadCredentialsException) {
            errorMessage = "invalid";
        }

        response.sendRedirect("/login?error=" + URLEncoder.encode(errorMessage, "UTF-8"));
    }
}
