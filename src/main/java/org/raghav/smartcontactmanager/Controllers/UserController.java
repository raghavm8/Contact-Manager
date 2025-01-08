package org.raghav.smartcontactmanager.Controllers;

import org.raghav.smartcontactmanager.Entities.User;
import org.raghav.smartcontactmanager.Helpers.Helper;
import org.raghav.smartcontactmanager.Services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/dashboard")
    public String Dashboard() {
        return "user/dashboard";
    }

    @PostMapping(value = "/dashboard")
    public String GetDashBoardAfterLogin() {
        return "user/dashboard";
    }

    @GetMapping("/profile")
    public String Profile(Model model, Authentication authentication) {

        return "user/profile";
    }
}
