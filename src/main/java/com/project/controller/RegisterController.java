package com.project.controller;


import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.model.User;
import com.project.repository.UserRepository;

@Controller
public class RegisterController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password,
                           @RequestParam String email, Model model) {
        // ตรวจสอบว่ามี Username ที่ซ้ำกันหรือไม่
        if (userRepository.findByUsername(username) != null) {
            model.addAttribute("messageregister", "Registration failed. Username already exists.");
            return "register";
        }

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        User user = new User();
        user.setUsername(username);
        user.setPassword(hashedPassword);
        user.setEmail(email);

        userRepository.save(user); // บันทึกข้อมูลผู้ใช้

        model.addAttribute("messageregister", "Registration successful! Welcome, " + username);
        return "login";
    }

}

