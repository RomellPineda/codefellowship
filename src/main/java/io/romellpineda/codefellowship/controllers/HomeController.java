package io.romellpineda.codefellowship.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.security.Principal;

// https://github.com/codefellows/seattle-java-401d7/blob/master/class-16/pumas/src/main/java/com/ncarignan/pumas/config/WebSecurityConfig.java referenced for this project

@Controller
public class HomeController {

    @GetMapping("/")
    public String index(Principal p, Model m) {
        if (p != null) {
            m.addAttribute("username", p.getName());
        }
        return "home";
    }

    @GetMapping("/sign-up")
    public String signUp() {
        return "signup";
    }

}
