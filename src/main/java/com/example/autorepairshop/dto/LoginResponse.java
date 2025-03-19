package com.example.autorepairshop.dto;

import java.util.List;

public record LoginResponse(String token, List<String> roles) {

}
