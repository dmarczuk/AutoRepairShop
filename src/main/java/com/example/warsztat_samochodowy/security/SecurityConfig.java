package com.example.warsztat_samochodowy.security;

import com.example.warsztat_samochodowy.error.MechanikNotFoundError;
import com.example.warsztat_samochodowy.model.Mechanik;
import com.example.warsztat_samochodowy.repository.MechanikRepository;
import com.example.warsztat_samochodowy.service.Mechanik_serwis;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig implements UserDetailsService{

    private final MechanikRepository mechanikRepository;

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
    public UserDetails loadUserByUsername(String username) throws MechanikNotFoundError {
        if ("admin".equals(username)) {
            //List<GrantedAuthority> adminAuthorities = new ArrayList<>();
            //adminAuthorities.add(new SimpleGrantedAuthority("ADMIN"));
            return User.builder()
                    .username("admin")
                    .password(passwordEncoder().encode("admin"))
                    .roles("ADMIN")
                    .build();
        }
        Mechanik mechanik = mechanikRepository.findByLogin(username)
                .orElseThrow(() -> new MechanikNotFoundError("Mechanik z podana nazwa użytkownika nie istnieje"));
        //List<GrantedAuthority> authorities = new ArrayList<>();
        //authorities.add(new SimpleGrantedAuthority("ROLE_MECHANIC"));
        return User.builder()
                .username(mechanik.getLogin())
                .password(mechanik.getHaslo())
                .roles("MECHANIC")
                .build();
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user = User.builder()
//                        .username("admin")
//                        .password(passwordEncoder().encode("admin"))
//                        .roles("ADMIN")
//                        .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/").permitAll()
//                        .requestMatchers("/test").permitAll()
//                        .requestMatchers("/klienci").hasRole("MECHANIC")
//                        .requestMatchers("/admin").hasRole("ADMIN")
//                        .anyRequest().authenticated()
//                )
//                .httpBasic(Customizer.withDefaults())
//                .formLogin(Customizer.withDefaults());
//
//        return http.build();
//    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        return new CustomUserDetailsService(mechanikRepository, passwordEncoder());
//    }
//
//    @AllArgsConstructor
//    public static class CustomUserDetailsService implements UserDetailsService {
//
//        private final MechanikRepository mechanikRepository;
//        private final PasswordEncoder passwordEncoder;
//
//        @Override
//        public UserDetails loadUserByUsername(String username) throws MechanikNotFoundError {
//            if ("admin".equals(username)) {
//                return User.builder()
//                        .username("admin")
//                        .password(passwordEncoder.encode("admin"))
//                        .roles("ADMIN")
//                        .build();
//            } else {
//                Mechanik mechanik = mechanikRepository.findByLogin(username)
//                        .orElseThrow(() -> new MechanikNotFoundError("Mechanik z podana nazwa użytkownika nie istnieje"));
//                List<GrantedAuthority> authorities = new ArrayList<>();
//                authorities.add(new SimpleGrantedAuthority("ROLE_MECHANIC"));
//                return new User(mechanik.getLogin(), mechanik.getHaslo(), authorities);
//            }
//        }
//    }


}