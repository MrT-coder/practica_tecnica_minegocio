package com.minegocio.facturacion.dto;

import com.minegocio.facturacion.modelo.DireccionCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DireccionClienteRepository extends JpaRepository<DireccionCliente, Long> {
    
    /**
     * CASO: Funcionalidad para listar las direcciones adicionales del cliente
     * Retorna todas las direcciones del cliente (incluye matriz)
     */
    List<DireccionCliente> findByClienteIdOrderByEsMatrizDescFechaCreacionAsc(Long clienteId);
    
    /**
     * CASO: Crear cliente con dirección matriz
     * Obtiene la dirección matriz para validación
     */
    Optional<DireccionCliente> findByClienteIdAndEsMatrizTrue(Long clienteId);
    
    /**
     * CASO: Registrar nueva dirección
     * Valida que no hay más de una dirección matriz
     */
    long countByClienteIdAndEsMatrizTrue(Long clienteId);
    
    /**
     * CASO: Crear cliente con dirección matriz
     * Verifica si un cliente ya tiene dirección matriz
     */
    boolean existsByClienteIdAndEsMatrizTrue(Long clienteId);
}