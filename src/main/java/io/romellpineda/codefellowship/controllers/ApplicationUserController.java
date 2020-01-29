package io.romellpineda.codefellowship.controllers;

import io.romellpineda.codefellowship.models.ApplicationUser;
import io.romellpineda.codefellowship.models.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

@Controller
public class ApplicationUserController {

    @Autowired
    ApplicationUserRepository appUserRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/join")
    public RedirectView createUser(String firstName, String lastName, String dateOfBirth, String bio, String username, String password){
        ApplicationUser newUser = new ApplicationUser(username, passwordEncoder.encode(password), firstName, lastName, dateOfBirth, bio);
        appUserRepo.save(newUser);

        return new RedirectView("/");
    }

    @GetMapping("login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/user/{id}")
    public String userDetails(@PathVariable Long id, Principal p, Model m) {
        ApplicationUser user = appUserRepo.findById(id).get();
        m.addAttribute("username", user.getUsername());
        m.addAttribute("principal", p.getName());
        return "userPage";
    }
}
