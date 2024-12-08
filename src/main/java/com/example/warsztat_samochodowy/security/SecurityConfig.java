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
                        .requestMatchers("/", "/login", "/dodaj/nowe/zgloszenie").permitAll()
                        .requestMatchers("/klienci", "/pojazdy", "/modyfikuj/dane/pojazdow", "/naprawy", "/przyjecie/naprawy").hasAnyRole("MECHANIC", "ADMIN")
                        .requestMatchers("/modyfikuj/opis_usterki", "/modyfikuj/rozpoczecie_naprawy", "/modyfikuj/zakonczenie_naprawy").hasAnyRole("MECHANIC", "ADMIN")
                        .requestMatchers("/dodaj/klienta", "/modyfikuj/dane/klienta", "/dodaj/pojazd", "/dodaj/mechanika", "mechanicy", "/zwolnij/mechanika").hasRole("ADMIN")
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
        Mechanic mechanik = mechanikRepository.findByLogin(username)
                .orElseThrow(() -> new MechanicNotFoundException("Mechanik z podana nazwa użytkownika nie istnieje"));
        return User.builder()
                .username(mechanik.getUsername())
                .password(mechanik.getPassword())
                .roles("MECHANIC")
                .build();
    }

}