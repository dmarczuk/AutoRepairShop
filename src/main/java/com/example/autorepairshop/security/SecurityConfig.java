package com.example.autorepairshop.security;

import com.example.autorepairshop.error.MechanicNotFoundException;
import com.example.autorepairshop.model.Mechanic;
import com.example.autorepairshop.repository.MechanicRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;

import static org.springframework.security.config.Customizer.withDefaults;

@AllArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig implements UserDetailsService{

    private final MechanicRepository mechanicRepository;
    private final JwtAuthenticationFilter jwtAuthTokenFilter;

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
        http.cors(corsCustomizer->corsCustomizer.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration corsConfiguration=new CorsConfiguration();
                        corsConfiguration.setAllowCredentials(true);// allows taking authentication with credentials
                        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:8080"));
                        // providing the allowed origin details, can provide multiple origins here, 7070 is the port number of client application here
                        corsConfiguration.setAllowedMethods(Collections.singletonList("*"));// allowing all HTTP methods GET,POST,PUT etc, can configure on your need
                        corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));// allowing all the request headers, can configure according to your need, which headers to allow
                        corsConfiguration.setMaxAge(Duration.ofMinutes(5L)); // setting the max time till which the allowed origin will not make a pre-flight request again to check if the CORS is allowed on not
                        return corsConfiguration;
                    }

                }))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/login","/clients", "/add/new/ticket", "/mechanics", "/repairs", "/cars", "/add/client", "/accept/repair", "/add/mechanic", "/add/car").permitAll()
                        //.requestMatchers("/clients", "/cars", "/modify/car", "/repairs", "/accept/repair").hasAnyRole("MECHANIC", "ADMIN")
                        .requestMatchers( "/modify/car").permitAll()
                        //.requestMatchers( "/modify/car").hasAnyRole("MECHANIC", "ADMIN")
                        .requestMatchers("/modify/description", "/modify/repairStartDate", "/modify/repairEndDate").permitAll()
                        //.requestMatchers("/modify/description", "/modify/repairStartDate", "/modify/repairEndDate").hasAnyRole("MECHANIC", "ADMIN")
                        .requestMatchers("/modify/client", "/fire/mechanic").permitAll()
                        //.requestMatchers("/modify/client", "/fire/mechanic").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .httpBasic(withDefaults())
                .addFilterBefore(jwtAuthTokenFilter, UsernamePasswordAuthenticationFilter.class);

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
                .password(passwordEncoder().encode(mechanik.getPassword()))
                .roles("MECHANIC")
                .build();
    }
}