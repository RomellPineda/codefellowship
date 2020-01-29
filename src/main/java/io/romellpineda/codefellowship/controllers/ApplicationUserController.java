package io.romellpineda.codefellowship.controllers;

import io.romellpineda.codefellowship.models.ApplicationUser;
import io.romellpineda.codefellowship.models.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

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
}
