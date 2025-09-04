package com.minegocio.facturacion.servicio;

import com.minegocio.facturacion.dto.*;
import com.minegocio.facturacion.modelo.Cliente;
import com.minegocio.facturacion.modelo.DireccionCliente;
import com.minegocio.facturacion.modelo.TipoIdentificacion;
import com.minegocio.facturacion.repositorio.ClienteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ClienteService {

    private final ClienteRepository clienteRepository;

    /**
     * CASO: Funcionalidad para buscar y obtener un listado de clientes
     */
    @Transactional(readOnly = true)
    public List<ClienteResponseDto> buscarClientes(String criterio) {
        log.info("Buscando clientes con criterio: {}", criterio);
        
        List<Cliente> clientes = new ArrayList<>();
        
        if (criterio == null || criterio.trim().isEmpty()) {
            // Si no hay criterio, devolver lista vacía según especificación
            return new ArrayList<>();
        }
        
        // Buscar por número de identificación 
        clienteRepository.findByNumeroIdentificacion(criterio)
            .ifPresent(clientes::add);
        
        // Buscar por número de identificación 
        List<Cliente> clientesPorNumero = clienteRepository.findByNumeroIdentificacionContaining(criterio);
        clientes.addAll(clientesPorNumero);
        
        // Buscar por nombres
        List<Cliente> clientesPorNombre = clienteRepository.findByNombresContainingIgnoreCase(criterio);
        clientes.addAll(clientesPorNombre);
        
        // Eliminar duplicados y convertir a DTO
        return clientes.stream()
                .distinct()
                .map(this::convertirAClienteResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * CASO: Funcionalidad para crear un nuevo cliente con la dirección matriz
     */
    public ClienteResponseDto crearCliente(ClienteCreateRequestDto request) {
        log.info("Creando cliente con número de identificación: {}", request.getNumeroIdentificacion());
        
        // Validar que no exista cliente con mismo número de identificación
        if (clienteRepository.existsByNumeroIdentificacion(request.getNumeroIdentificacion())) {
            throw new IllegalArgumentException("Ya existe un cliente con el número de identificación: " + request.getNumeroIdentificacion());
        }
        
        // Crear cliente
        Cliente cliente = Cliente.builder()
                .tipoIdentificacion(TipoIdentificacion.valueOf(request.getTipoIdentificacion()))
                .numeroIdentificacion(request.getNumeroIdentificacion())
                .nombres(request.getNombres())
                .correo(request.getCorreo())
                .numeroCelular(request.getNumeroCelular())
                .build();
        
        // Crear dirección matriz
        DireccionCliente direccionMatriz = DireccionCliente.builder()
                .provincia(request.getDireccionMatriz().getProvincia())
                .ciudad(request.getDireccionMatriz().getCiudad())
                .direccion(request.getDireccionMatriz().getDireccion())
                .esMatriz(true)
                .cliente(cliente)
                .build();
        
        // Agregar dirección al cliente
        cliente.agregarDireccion(direccionMatriz);
        
        // Guardar cliente (cascada guarda la dirección)
        Cliente clienteGuardado = clienteRepository.save(cliente);
        
        log.info("Cliente creado exitosamente con ID: {}", clienteGuardado.getId());
        return convertirAClienteResponseDto(clienteGuardado);
    }

    /**
     * CASO: Funcionalidad para editar los datos del cliente
     */
    public ClienteResponseDto editarCliente(Long id, ClienteUpdateRequestDto request) {
        log.info("Editando cliente con ID: {}", id);
        
        // Buscar cliente existente
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con ID: " + id));
        
        // Validar que no exista otro cliente con mismo número de identificación
        clienteRepository.findByNumeroIdentificacion(request.getNumeroIdentificacion())
                .ifPresent(clienteExistente -> {
                    if (!clienteExistente.getId().equals(id)) {
                        throw new IllegalArgumentException("Ya existe otro cliente con el número de identificación: " + request.getNumeroIdentificacion());
                    }
                });
        
        // Actualizar solo datos del cliente
        cliente.setTipoIdentificacion(TipoIdentificacion.valueOf(request.getTipoIdentificacion()));
        cliente.setNumeroIdentificacion(request.getNumeroIdentificacion());
        cliente.setNombres(request.getNombres());
        cliente.setCorreo(request.getCorreo());
        cliente.setNumeroCelular(request.getNumeroCelular());
        
        // Guardar cambios
        Cliente clienteActualizado = clienteRepository.save(cliente);
        
        log.info("Cliente actualizado exitosamente con ID: {}", clienteActualizado.getId());
        return convertirAClienteResponseDto(clienteActualizado);
    }

    /**
     * CASO: Funcionalidad para eliminar un cliente
     */
    public void eliminarCliente(Long id) {
        log.info("Eliminando cliente con ID: {}", id);
        
        // Verificar que el cliente existe
        if (!clienteRepository.existsById(id)) {
            throw new IllegalArgumentException("Cliente no encontrado con ID: " + id);
        }
        
        clienteRepository.deleteById(id);
        
        log.info("Cliente eliminado exitosamente con ID: {}", id);
    }


    /**
     * Convertir entidad Cliente a DTO de respuesta
     */
    private ClienteResponseDto convertirAClienteResponseDto(Cliente cliente) {
        DireccionResponseDto direccionMatriz = null;
        
        // Obtener dirección matriz
        DireccionCliente direccion = cliente.getDireccionMatriz();
        if (direccion != null) {
            direccionMatriz = DireccionResponseDto.builder()
                    .id(direccion.getId())
                    .provincia(direccion.getProvincia())
                    .ciudad(direccion.getCiudad())
                    .direccion(direccion.getDireccion())
                    .esMatriz(direccion.getEsMatriz())
                    .fechaCreacion(direccion.getFechaCreacion())
                    .fechaActualizacion(direccion.getFechaActualizacion())
                    .build();
        }
        
        return ClienteResponseDto.builder()
                .id(cliente.getId())
                .tipoIdentificacion(cliente.getTipoIdentificacion().name())
                .numeroIdentificacion(cliente.getNumeroIdentificacion())
                .nombres(cliente.getNombres())
                .correo(cliente.getCorreo())
                .numeroCelular(cliente.getNumeroCelular())
                .direccionMatriz(direccionMatriz)
                .fechaCreacion(cliente.getFechaCreacion())
                .fechaActualizacion(cliente.getFechaActualizacion())
                .build();
    }
}
