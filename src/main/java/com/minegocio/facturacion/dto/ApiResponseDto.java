package com.minegocio.facturacion.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO genérico para respuestas de la API
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponseDto {
    
    private boolean success;
    
    private String message;
    
    private Object data;
    
    @JsonProperty("timestamp")
    private String timestamp;
    
    public static ApiResponseDto success(String message) {
        return ApiResponseDto.builder()
                .success(true)
                .message(message)
                .timestamp(java.time.LocalDateTime.now().toString())
                .build();
    }
    
    public static ApiResponseDto success(String message, Object data) {
        return ApiResponseDto.builder()
                .success(true)
                .message(message)
                .data(data)
                .timestamp(java.time.LocalDateTime.now().toString())
                .build();
    }
    
    public static ApiResponseDto error(String message) {
        return ApiResponseDto.builder()
                .success(false)
                .message(message)
                .timestamp(java.time.LocalDateTime.now().toString())
                .build();
    }
}
