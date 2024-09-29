package org.raghav.smartcontactmanager.Controllers;

import org.raghav.smartcontactmanager.Configurations.AuthenticationHandlers.OAuthSuccessAuthenticationHandler;
import org.raghav.smartcontactmanager.Helpers.Helper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping(value = "/dashboard")
    public String Dashboard() {
        return "user/dashboard";
    }

    @PostMapping(value = "/dashboard")
    public String GetDashBoardAfterLogin() {
        return "user/dashboard";
    }

    @GetMapping("/profile")
    public String Profile(Authentication authentication) {
        String userName = Helper.GetEmailIdOfLoggedInUser(authentication);
        logger.info("User profile: " + userName);
        return "user/profile";
    }
}
