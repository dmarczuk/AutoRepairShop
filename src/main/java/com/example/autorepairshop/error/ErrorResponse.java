package com.example.autorepairshop.error;

import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
record ErrorResponse (String message, HttpStatus status) {
}
