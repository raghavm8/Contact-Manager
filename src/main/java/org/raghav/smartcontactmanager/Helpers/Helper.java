package org.raghav.smartcontactmanager.Helpers;

import org.raghav.smartcontactmanager.Controllers.UserController;
import org.raghav.smartcontactmanager.Entities.Providers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.security.Principal;

public class Helper {

    private static Logger logger = LoggerFactory.getLogger(Helper.class);

    public static String GetEmailIdOfLoggedInUser(Authentication authentication) {
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
            String authorizedClientRegistrationId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
            String userName = "";

            if (authorizedClientRegistrationId.equalsIgnoreCase(Providers.GOOGLE.toString())) {
                logger.info("Inside Google Provider");
                userName = oAuth2User.getAttribute("email").toString();
            } else if (authorizedClientRegistrationId.equalsIgnoreCase(Providers.GITHUB.toString())) {
                logger.info("Inside Github Provider");
                userName = oAuth2User.getAttribute("email") != null ? oAuth2User.getAttribute("email").toString()
                        : oAuth2User.getAttribute("login").toString() + "@gmail.com";
            }
            return userName;
        } else {
            logger.info("Getting data from local database");
            return authentication.getName();
        }
    }
}
