package com.tientv.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping
    public Map<String, Object> getUser(@AuthenticationPrincipal OAuth2User principal) {
        if (principal != null) {
            // Extract desired attributes from the OAuth2User principal
            // Common attributes include 'name', 'email', 'picture', etc.
            // The exact attribute names depend on the scopes requested (openid, profile, email)
            return Map.of(
                    "name", principal.getAttribute("name"),
                    "email", principal.getAttribute("email"),
                    "picture", principal.getAttribute("picture")
                    // Add other attributes as needed
            );
        }
        // Return empty map or handle unauthenticated user case appropriately
        return Collections.emptyMap();
    }
}
