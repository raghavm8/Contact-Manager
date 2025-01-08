package org.raghav.smartcontactmanager.Controllers;

import org.raghav.smartcontactmanager.Entities.User;
import org.raghav.smartcontactmanager.Helpers.Helper;
import org.raghav.smartcontactmanager.Services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class RootController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);
    private UserService userService;

    public RootController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute
    public void SetLoggedInUserInformation(Model model, Authentication authentication) {
        if (authentication == null)
            return;

        String userName = Helper.GetEmailIdOfLoggedInUser(authentication);
        logger.info("User profile: " + userName);
        User user = userService.getUserByEmail(userName);

        if (user == null) {
            model.addAttribute("LoggedIn", null);
        } else {
            model.addAttribute("LoggedInUser", user);
        }
        System.out.println(user);
    }
}
