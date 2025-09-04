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
     * Busca por número de identificación exacto
     */
    Optional<Cliente> findByNumeroIdentificacion(String numeroIdentificacion);
    
    /**
     * CASO: Funcionalidad para buscar y obtener un listado de clientes
     * Busca por nombres que contengan el texto (case insensitive)
     */
    List<Cliente> findByNombresContainingIgnoreCase(String nombres);
    
    /**
     * CASO: Funcionalidad para buscar y obtener un listado de clientes
     * Para búsquedas parciales por número de identificación
     */
    List<Cliente> findByNumeroIdentificacionContaining(String numeroIdentificacion);
    
    /**
     * CASO: Editar cliente, Eliminar cliente, Crear cliente
     * Obtiene cliente con sus direcciones incluyendo la matriz
     */
    @EntityGraph(attributePaths = {"direcciones"})
    Optional<Cliente> findById(Long id);
    
    /**
     * CASO: Crear cliente, Editar cliente
     * Valida que no exista más de un cliente con el mismo número de identificación
     */
    boolean existsByNumeroIdentificacion(String numeroIdentificacion);
}