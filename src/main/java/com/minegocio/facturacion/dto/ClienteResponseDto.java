package com.minegocio.facturacion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

/**
 * CASO: Funcionalidad para buscar y obtener un listado de clientes
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteResponseDto {
    
    private Long id;
    
    @JsonProperty("tipo_identificacion")
    private String tipoIdentificacion;
    
    @JsonProperty("numero_identificacion")
    private String numeroIdentificacion;
    
    private String nombres;
    
    private String correo;
    
    @JsonProperty("numero_celular")
    private String numeroCelular;
    
    @JsonProperty("direccion_matriz")
    private DireccionResponseDto direccionMatriz;
    
    @JsonProperty("fecha_creacion")
    private LocalDateTime fechaCreacion;
    
    @JsonProperty("fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
}