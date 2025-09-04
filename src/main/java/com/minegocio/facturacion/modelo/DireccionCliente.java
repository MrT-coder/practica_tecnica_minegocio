package com.minegocio.facturacion.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "direcciones_cliente")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DireccionCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "provincia", nullable = false, length = 50)
    @NotBlank(message = "La provincia es obligatoria")
    @Size(min = 2, max = 50, message = "La provincia debe tener entre 2 y 50 caracteres")
    private String provincia;
    
    @Column(name = "ciudad", nullable = false, length = 50)
    @NotBlank(message = "La ciudad es obligatoria")
    @Size(min = 2, max = 50, message = "La ciudad debe tener entre 2 y 50 caracteres")
    private String ciudad;
    
    @Column(name = "direccion", nullable = false, length = 200)
    @NotBlank(message = "La dirección es obligatoria")
    @Size(min = 5, max = 200, message = "La dirección debe tener entre 5 y 200 caracteres")
    private String direccion;
    
    @Column(name = "es_matriz", nullable = false)
    @Builder.Default
    private Boolean esMatriz = false;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    @NotNull(message = "El cliente es obligatorio")
    private Cliente cliente;
    
    @CreationTimestamp
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;
    
    @UpdateTimestamp
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
    
    // Verificar si es matriz
    public boolean esMatriz() {
        return Boolean.TRUE.equals(esMatriz);
    }
}
