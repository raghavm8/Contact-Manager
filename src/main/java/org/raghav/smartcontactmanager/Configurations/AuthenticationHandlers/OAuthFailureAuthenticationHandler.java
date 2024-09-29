package org.raghav.smartcontactmanager.Configurations.AuthenticationHandlers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuthFailureAuthenticationHandler implements AuthenticationFailureHandler {

    Logger logger = LoggerFactory.getLogger(OAuthFailureAuthenticationHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        logger.info("Authentication Failed");
        logger.info("OAuthFailureAuthenticationHandler");

        new DefaultRedirectStrategy().sendRedirect(request, response, "/login?error=true");
    }
}
