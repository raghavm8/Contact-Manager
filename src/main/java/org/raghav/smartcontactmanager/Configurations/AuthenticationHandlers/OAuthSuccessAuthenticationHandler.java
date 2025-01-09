package org.raghav.smartcontactmanager.Configurations.AuthenticationHandlers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.raghav.smartcontactmanager.Entities.Providers;
import org.raghav.smartcontactmanager.Entities.Roles;
import org.raghav.smartcontactmanager.Entities.User;
import org.raghav.smartcontactmanager.Repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Component
public class OAuthSuccessAuthenticationHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private Logger logger = LoggerFactory.getLogger(OAuthSuccessAuthenticationHandler.class);

    public OAuthSuccessAuthenticationHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        logger.info("Authentication success");
        logger.info("onAuthenticationSuccess");

        OAuth2AuthenticationToken authenticationToken = (OAuth2AuthenticationToken) authentication;
        String authorizedClientRegistrationId = authenticationToken.getAuthorizedClientRegistrationId();

        logger.info("provider = " + authorizedClientRegistrationId);
        DefaultOAuth2User oAuthUser = (DefaultOAuth2User) authentication.getPrincipal();
        oAuthUser.getAttributes().forEach((key, value) -> {
            logger.info(key + " = " + value);
        });

        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setRolesList(List.of(Roles.ROLE_USER));
        user.setEmailVerified(true);
        user.setEnabled(true);

        if (authorizedClientRegistrationId.equalsIgnoreCase(Providers.GOOGLE.toString())) {
            user.setEmail(oAuthUser.getAttribute("email").toString());
            user.setProfilePic(oAuthUser.getAttribute("picture").toString());
            user.setName(oAuthUser.getAttribute("name").toString());
            user.setProviderUserId(oAuthUser.getName());
            user.setProvider(Providers.GOOGLE);
            user.setAbout("This account is created using google.");
        } else if (authorizedClientRegistrationId.equalsIgnoreCase(Providers.GITHUB.toString())) {
            String email = oAuthUser.getAttribute("email") != null ? oAuthUser.getAttribute("email").toString()
                    : oAuthUser.getAttribute("login").toString() + "@gmail.com";
            String picture = oAuthUser.getAttribute("avatar_url").toString();
            String name = oAuthUser.getAttribute("login").toString();
            String providerUserId = oAuthUser.getName();

            user.setEmail(email);
            user.setProfilePic(picture);
            user.setName(name);
            user.setProviderUserId(providerUserId);
            user.setProvider(Providers.GITHUB);
            user.setAbout("This account is created using github");
        } else {
            logger.info("Unknown Provider: " + authorizedClientRegistrationId);
        }

        Random random = new Random();

        Long tenDigitNumber = 1000000000L + (long) (random.nextDouble() * 9000000000L);
        user.setPhoneNumber(tenDigitNumber.toString());
        user.setPhoneVerified(false);

        User GetUserByEmail = userRepository.findByEmail(user.getEmail());
        if (GetUserByEmail == null) {
            userRepository.save(user);
        }

        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");
    }
}
