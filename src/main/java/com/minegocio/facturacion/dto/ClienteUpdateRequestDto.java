package com.minegocio.facturacion.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CASO: Funcionalidad para editar los datos del cliente
 * DTO de request para actualizar solo datos del cliente (NO direcciones)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteUpdateRequestDto {
    
    @NotBlank(message = "El tipo de identificación es obligatorio")
    @Pattern(regexp = "^(CEDULA|RUC)$", message = "El tipo de identificación debe ser CEDULA o RUC")
    @JsonProperty("tipo_identificacion")
    private String tipoIdentificacion;
    
    @NotBlank(message = "El número de identificación es obligatorio")
    @Size(min = 10, max = 20, message = "El número de identificación debe tener entre 10 y 20 caracteres")
    @JsonProperty("numero_identificacion")
    private String numeroIdentificacion;
    
    @NotBlank(message = "Los nombres son obligatorios")
    @Size(min = 2, max = 100, message = "Los nombres deben tener entre 2 y 100 caracteres")
    private String nombres;
    
    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El formato del correo no es válido")
    @Size(max = 100, message = "El correo no puede exceder 100 caracteres")
    private String correo;
    
    @NotBlank(message = "El número de celular es obligatorio")
    @Pattern(regexp = "^[0-9+\\-\\s]+$", message = "El número de celular solo puede contener números, +, - y espacios")
    @Size(min = 10, max = 15, message = "El número de celular debe tener entre 10 y 15 caracteres")
    @JsonProperty("numero_celular")
    private String numeroCelular;
}
