package com.tientv.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.tientv.repository.UserRepository;
import com.tientv.services.OAuth2LoginSuccessHandler;

import java.util.Arrays;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private UserRepository userRepository;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/", "/login**", "/error**").permitAll()
                        .requestMatchers("/api/logout").permitAll() // Allow POST to /api/logout without CSRF
                        .anyRequest().authenticated())
                .oauth2Login(oauth2Login -> oauth2Login
                        .successHandler(new OAuth2LoginSuccessHandler(userRepository)))
                .logout(logout -> logout
                        .logoutUrl("/api/logout") // Specify logout URL
                        .logoutSuccessHandler((request, response, authentication) -> {
                            System.out.println("Backend logout success handler called");
                            System.out.println("Logout successful from backend");
                            System.out.println("Session ID before invalidation: " + request.getSession().getId());
                            request.getSession().invalidate();
                            System.out.println("Session invalidated");
                            System.out.println("Session ID after invalidation (should be new or invalid): "
                                    + request.getSession().getId());
                        })
                        .deleteCookies("JSESSIONID") // Delete the session cookie on logout
                        .clearAuthentication(true) // Clear authentication on logout
                        .invalidateHttpSession(true) // Invalidate the session on logout
                )
                .csrf(csrf -> csrf.ignoringRequestMatchers("/api/logout")) // Disable CSRF for /api/logout only
                .cors(withDefaults());

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
