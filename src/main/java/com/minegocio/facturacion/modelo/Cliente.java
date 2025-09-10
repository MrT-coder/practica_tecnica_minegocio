package com.minegocio.facturacion.modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// Usamos Lombok 
@Entity // Indica que es una entidad de JPA
@Table(name = "clientes")
@Data // Crea getters, setters, toString, equals y hashCode
@NoArgsConstructor // Crea el constructor sin argumentos
@AllArgsConstructor // Crea el constructor con todos los argumentos
@Builder // Crea el patrón de diseño Builder, lo usamos para crear instancias de la clase

public class Cliente {

    // Usamos JPA para mapear la clase a una tabla de base de datos
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id; // Lo hacemos private para encapsular los datos
    
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_identificacion", nullable = false)
    @NotNull(message = "El tipo de identificación es obligatorio") // Validamos a nivel de aplicación
    private TipoIdentificacion tipoIdentificacion;
    
    @Column(name = "numero_identificacion", nullable = false, unique = true, length = 20)
    @NotBlank(message = "El número de identificación es obligatorio") // Valiamos el String no sea nulo ni vacío
    @Size(min = 10, max = 20, message = "El número de identificación debe tener entre 10 y 20 caracteres") // Validamos el tamaño del String
    private String numeroIdentificacion;
    
    @Column(name = "nombres", nullable = false, length = 100)
    @NotBlank(message = "Los nombres son obligatorios")
    @Size(min = 2, max = 100, message = "Los nombres deben tener entre 2 y 100 caracteres")
    private String nombres;
    
    @Column(name = "correo", nullable = false, length = 100)
    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El formato del correo no es válido")
    @Size(max = 100, message = "El correo no puede exceder 100 caracteres")
    private String correo;
    
    @Column(name = "numero_celular", nullable = false, length = 15)
    @NotBlank(message = "El número de celular es obligatorio")
    @Pattern(regexp = "^[0-9+\\-\\s]+$", message = "El número de celular solo puede contener números, +, - y espacios")
    @Size(min = 10, max = 15, message = "El número de celular debe tener entre 10 y 15 caracteres")
    private String numeroCelular;
    
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<DireccionCliente> direcciones = new ArrayList<>();
    
    @CreationTimestamp
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;
    
    @UpdateTimestamp
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
    
    // Obtener la dirección matriz
    public DireccionCliente getDireccionMatriz() {
        return direcciones.stream()
                .filter(DireccionCliente::getEsMatriz)
                .findFirst()
                .orElse(null);
    }
    
    // Agregar una dirección
    public void agregarDireccion(DireccionCliente direccion) {
        direcciones.add(direccion);
        direccion.setCliente(this);
    }
    
    // Remover una dirección
    public void removerDireccion(DireccionCliente direccion) {
        direcciones.remove(direccion);
        direccion.setCliente(null);
    }
 }
