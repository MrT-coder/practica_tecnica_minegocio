package com.minegocio.facturacion.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * CASO: Listar direcciones, Respuesta con dirección matriz
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DireccionResponseDto {
    
    private Long id;
    
    private String provincia;
    
    private String ciudad;
    
    private String direccion;
    
    @JsonProperty("es_matriz") // Anotación para mapear el nombre JSON
    private Boolean esMatriz;
    
    @JsonProperty("fecha_creacion")
    private LocalDateTime fechaCreacion;
    
    @JsonProperty("fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
}
