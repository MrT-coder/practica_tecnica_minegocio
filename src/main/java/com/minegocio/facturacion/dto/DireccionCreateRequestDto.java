package com.minegocio.facturacion.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CASO: Crear cliente con dirección matriz, Registrar nueva dirección
 * DTO de request para crear direcciones
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DireccionCreateRequestDto {
    
    @NotBlank(message = "La provincia es obligatoria")
    @Size(min = 2, max = 50, message = "La provincia debe tener entre 2 y 50 caracteres")
    private String provincia;
    
    @NotBlank(message = "La ciudad es obligatoria")
    @Size(min = 2, max = 50, message = "La ciudad debe tener entre 2 y 50 caracteres")
    private String ciudad;
    
    @NotBlank(message = "La dirección es obligatoria")
    @Size(min = 5, max = 200, message = "La dirección debe tener entre 5 y 200 caracteres")
    private String direccion;
}
