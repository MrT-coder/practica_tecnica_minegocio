package com.minegocio.facturacion.repositorio;

import com.minegocio.facturacion.modelo.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
    /**
     * CASO: Funcionalidad para buscar y obtener un listado de clientes
     */
    Optional<Cliente> findByNumeroIdentificacion(String numeroIdentificacion);
    
    /**
     * CASO: Funcionalidad para buscar y obtener un listado de clientes
     */
    List<Cliente> findByNombresContainingIgnoreCase(String nombres);
    
    /**
     * CASO: Funcionalidad para buscar y obtener un listado de clientes
     */
    List<Cliente> findByNumeroIdentificacionContaining(String numeroIdentificacion);
    
    /**
     * CASO: Editar cliente, Eliminar cliente, Crear cliente
     */
    @EntityGraph(attributePaths = {"direcciones"})
    Optional<Cliente> findById(Long id);
    
    /**
     * CASO: Crear cliente, Editar cliente
     */
    boolean existsByNumeroIdentificacion(String numeroIdentificacion);
}