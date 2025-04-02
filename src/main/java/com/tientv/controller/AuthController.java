package com.tientv.controller;

import com.tientv.model.User;
import com.tientv.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login/success")
    public ResponseEntity<?> loginSuccess(@AuthenticationPrincipal OAuth2User principal) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        String email = principal.getAttribute("email");
        String name = principal.getAttribute("name");
        String avatarUrl = principal.getAttribute("picture");
        response.put("email", email);
        response.put("name", name);
        response.put("avatar", avatarUrl);

        Optional<User> userOptional = userRepository.findByEmail(email);
        User user = null;
        if (!userOptional.isPresent()) {
            user = new User();
            user.setEmail(email);
            user.setName(name);
            user.setAvatar(avatarUrl);
            user = userRepository.save(user);
        } else {
            user = userOptional.get();
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/login/error")
    public ResponseEntity<?> loginError() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "error");
        response.put("message", "Login failed");
        return ResponseEntity.badRequest().body(response);
    }
}
