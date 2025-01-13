package com.example.autorepairshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AutoRepairShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutoRepairShopApplication.class, args);
    }


//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/clients").allowedOrigins("http://localhost:3000");
//            }
//        };
//    }

}
