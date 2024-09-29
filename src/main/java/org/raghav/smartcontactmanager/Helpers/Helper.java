package org.raghav.smartcontactmanager.Helpers;

import org.raghav.smartcontactmanager.Entities.Providers;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.security.Principal;

public class Helper {
    public static String GetEmailIdOfLoggedInUser(Authentication authentication) {
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
            String authorizedClientRegistrationId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
            String userName = "";

            if (authorizedClientRegistrationId.equalsIgnoreCase(Providers.GOOGLE.toString())) {
                System.out.println("Inside Google Provider");
                userName = oAuth2User.getAttribute("email").toString();
            } else if (authorizedClientRegistrationId.equalsIgnoreCase(Providers.GITHUB.toString())) {
                System.out.println("Inside Github Provider");
                userName = oAuth2User.getAttribute("email") != null ? oAuth2User.getAttribute("email").toString()
                        : oAuth2User.getAttribute("login").toString() + "@gmail.com";
            }
            return userName;
        } else {
            System.out.println("Getting data from local database");
            return authentication.getName();
        }
    }
}
