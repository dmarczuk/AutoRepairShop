package com.example.warsztat_samochodowy.security;

import com.example.warsztat_samochodowy.error.MechanicNotFoundException;
import com.example.warsztat_samochodowy.model.Mechanic;
import com.example.warsztat_samochodowy.repository.MechanicRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@AllArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig implements UserDetailsService{

    private final MechanicRepository mechanicRepository;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/login", "/add/new/ticket").permitAll()
                        .requestMatchers("/clients", "/cars", "/modify/car", "/repairs", "/accept/repair").hasAnyRole("MECHANIC", "ADMIN")
                        .requestMatchers("/modify/description", "/modify/repairStartDate", "/modify/repairEndDate").hasAnyRole("MECHANIC", "ADMIN")
                        .requestMatchers("/add/client", "/modify/client", "/add/car", "/add/mechanic", "/mechanics", "/fire/mechanic").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults());

        return http.build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws MechanicNotFoundException {
        if ("admin".equals(username)) {
            return User.builder()
                    .username("admin")
                    .password(passwordEncoder().encode("admin"))
                    .roles("ADMIN")
                    .build();
        }
        Mechanic mechanik = mechanicRepository.findByUsername(username)
                .orElseThrow(() -> new MechanicNotFoundException("Mechanic with the given username not found"));
        return User.builder()
                .username(mechanik.getUsername())
                .password(mechanik.getPassword())
                .roles("MECHANIC")
                .build();
    }
}