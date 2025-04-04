package com.tientv.controller;

import com.tientv.model.User;
import com.tientv.repository.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public Map<String, Object> getUser(@AuthenticationPrincipal OAuth2User principal) {
        if (principal != null) {
            String email = principal.getAttribute("email");

            // Giả sử bạn có repository để truy vấn thông tin người dùng từ DB
            Optional<User> user = userRepository.findByEmail(email);
            return Map.of(
                    "name", principal.getAttribute("name"),
                    "email", principal.getAttribute("email"),
                    "picture", principal.getAttribute("picture"),
                    "role", user.get().getRole()
            );
        }
        // Return empty map or handle unauthenticated user case appropriately
        return Collections.emptyMap();
    }
}
