package com.project.controller;


import com.project.model.User;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.repository.UserRepository;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        User user = userRepository.findByUsername(username);

        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            model.addAttribute("message", "Login successful! Welcome, " + username);
        } else {
            model.addAttribute("message", "Login failed. Please try again.");
        }
        return "redirect:/";
        //return "login";
    }
}
