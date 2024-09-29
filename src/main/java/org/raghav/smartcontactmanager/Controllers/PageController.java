package org.raghav.smartcontactmanager.Controllers;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.raghav.smartcontactmanager.Dto.UserFormDto;
import org.raghav.smartcontactmanager.Entities.User;
import org.raghav.smartcontactmanager.Helpers.Message;
import org.raghav.smartcontactmanager.Helpers.MessageType;
import org.raghav.smartcontactmanager.Services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PageController {

    private UserService userService;
    public PageController(UserService userService){
        this.userService = userService;
    }

    @RequestMapping("/")
    public String index() {
        return "home";
    }

    @RequestMapping("/home")
    public String home(Model model) {
        // sending data to view
        model.addAttribute("name", "Substring Technologies");
        model.addAttribute("youtubeChannel", "Learn Code With Durgesh");
        model.addAttribute("githubRepo", "https://github.com/learncodewithdurgesh/");
        return "home";
    }

    @RequestMapping("/about")
    public String aboutPage(Model model) {
        model.addAttribute("isLogin", true);
        System.out.println("About page loading");
        return "about";
    }

    @RequestMapping("/services")
    public String servicesPage() {
        System.out.println("services page loading");
        return "services";
    }

    @RequestMapping("/login")
    public String loginPage() {
        System.out.println("services page loading");
        return "login";
    }

    @RequestMapping("/register")
    public String registerPage(Model model) {
        System.out.println("services page loading");
        UserFormDto userFormDto = new UserFormDto();
        model.addAttribute("userFormDto", userFormDto);
        return "register";
    }

    @RequestMapping("/contact")
    public String contactsPage() {
        System.out.println("services page loading");
        return "contacts";
    }

    @RequestMapping(value = "/do-register", method = RequestMethod.POST)
    public String processRegister(@Valid @ModelAttribute UserFormDto userFormDto, BindingResult bindingResult, HttpSession session) {

        if(bindingResult.hasErrors())
            return "register";

        System.out.println("Name = " + userFormDto.getName());
        User user = new User();
        user.setName(userFormDto.getName());
        user.setEmail(userFormDto.getEmail());
        user.setPassword(userFormDto.getPassword());
        user.setAbout(userFormDto.getAbout());
        user.setPhoneNumber(userFormDto.getPhone());
        user.setEnabled(true);
        user.setProfilePic("https://www.learncodewithdurgesh.com/_next/image?url=%2F_next%2Fstatic%2Fmedia%2Fdurgesh_sir.35c6cb78.webp&w=1920&q=75");

        User savedUser = userService.saveUser(user);
        System.out.println(savedUser);

        Message message = Message.builder().content("Registration Successful").type(MessageType.green).build();
        System.out.println(message);
        session.setAttribute("message", message);
        return "redirect:/register";
    }


}
