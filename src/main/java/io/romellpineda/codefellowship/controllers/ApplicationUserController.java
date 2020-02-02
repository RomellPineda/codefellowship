package io.romellpineda.codefellowship.controllers;

import io.romellpineda.codefellowship.models.ApplicationUser;
import io.romellpineda.codefellowship.models.ApplicationUserRepository;
import io.romellpineda.codefellowship.models.Post;
import io.romellpineda.codefellowship.models.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class ApplicationUserController {

    @Autowired
    ApplicationUserRepository appUserRepo;

    @Autowired
    PostRepository postRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/join")
    public RedirectView createUser(HttpServletRequest req, String firstName, String lastName, String dateOfBirth, String bio, String username, String password){
        ApplicationUser newUser = new ApplicationUser(username, passwordEncoder.encode(password), firstName, lastName, dateOfBirth, bio);
        appUserRepo.save(newUser);

        // https://docs.oracle.com/javaee/6/api/javax/servlet/http/HttpServletRequest.html was referenced
        // https://www.codejava.net/frameworks/struts/how-to-access-httpservletrequest-and-httpservletresponse-within-struts2-action-class was referenced
        try {
            req.login(username, password);
        } catch (ServletException e) {
            e.printStackTrace();
        }

        return new RedirectView("/myprofile");
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }
    

    @GetMapping("/myprofile")
    public String userDetails(Principal p, Model m) {
        ApplicationUser authenticatedUser = appUserRepo.findByUsername(p.getName());
        m.addAttribute("principal", authenticatedUser);
        return "userPage";
    }

    @PostMapping("/user/post")
    public RedirectView recordPost(Principal p, String body) {
        ApplicationUser user = appUserRepo.findByUsername(p.getName());
        Post userPost = new Post(user, body);
        postRepo.save(userPost);
        return new RedirectView("/myprofile");
    }

    @GetMapping("/userPage/{id}")
    public String otherUserPage(@PathVariable Long id, Model m) {
        ApplicationUser otherUser = appUserRepo.findById(id).get();
        m.addAttribute("member", otherUser);
        return "otherUser";
    }

    @PostMapping("/follow")
    public String followMember(Principal p, Model m, String username) {
        ApplicationUser loggedUser = appUserRepo.findByUsername(p.getName());
        ApplicationUser otherUser = appUserRepo.findByUsername(username);
        loggedUser.iFollow.add(otherUser);
//        appUserRepo.save(loggedUser);
        m.addAttribute("following", loggedUser.iFollow);
        System.out.println(loggedUser.iFollow);
        return "feed";
    }
}
