package io.romellpineda.codefellowship.controllers;

import io.romellpineda.codefellowship.models.ApplicationUser;
import io.romellpineda.codefellowship.models.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.security.Principal;
import java.util.List;

// https://github.com/codefellows/seattle-java-401d7/blob/master/class-16/pumas/src/main/java/com/ncarignan/pumas/config/WebSecurityConfig.java referenced for this project

@Controller
public class HomeController {

    @Autowired
    ApplicationUserRepository appUserRepo;

    @GetMapping("/")
    public String index(Principal p, Model m) {
        if (p != null) {
            m.addAttribute("username", p.getName());
        }

        List<ApplicationUser> members = appUserRepo.findAll();
        m.addAttribute("members", members);

        return "home";
    }

    @GetMapping("/sign-up")
    public String signUp() {
        return "signup";
    }

}
