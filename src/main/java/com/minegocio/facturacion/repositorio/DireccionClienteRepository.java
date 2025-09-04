package com.minegocio.facturacion.repositorio;

import com.minegocio.facturacion.modelo.DireccionCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DireccionClienteRepository extends JpaRepository<DireccionCliente, Long> {
    
    /**
     * CASO: Funcionalidad para listar las direcciones adicionales del cliente
     */
    List<DireccionCliente> findByClienteIdOrderByEsMatrizDescFechaCreacionAsc(Long clienteId);
    
    /**
     * CASO: Crear cliente con dirección matriz
     */
    Optional<DireccionCliente> findByClienteIdAndEsMatrizTrue(Long clienteId);
    
    /**
     * CASO: Registrar nueva dirección
     */
    long countByClienteIdAndEsMatrizTrue(Long clienteId);
    
    /**
     * CASO: Crear cliente con dirección matriz
     */
    boolean existsByClienteIdAndEsMatrizTrue(Long clienteId);
}