package com.minegocio.facturacion.servicio;

import com.minegocio.facturacion.dto.DireccionCreateRequestDto;
import com.minegocio.facturacion.dto.DireccionResponseDto;
import com.minegocio.facturacion.modelo.Cliente;
import com.minegocio.facturacion.modelo.DireccionCliente;
import com.minegocio.facturacion.repositorio.ClienteRepository;
import com.minegocio.facturacion.repositorio.DireccionClienteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class DireccionClienteService {

    private final DireccionClienteRepository direccionClienteRepository;
    private final ClienteRepository clienteRepository;

    /**
     * CASO: Funcionalidad para registrar una nueva dirección por cliente
     */
    public void registrarNuevaDireccion(Long clienteId, DireccionCreateRequestDto request) {
        log.info("Registrando nueva dirección para cliente ID: {}", clienteId);
        
        // Verificar que el cliente existe
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con ID: " + clienteId));
        
        // Crear nueva dirección (siempre como NO matriz, ya que el cliente ya tiene una matriz)
        DireccionCliente nuevaDireccion = DireccionCliente.builder()
                .provincia(request.getProvincia())
                .ciudad(request.getCiudad())
                .direccion(request.getDireccion())
                .esMatriz(false) // Las direcciones adicionales nunca son matriz
                .cliente(cliente)
                .build();
        
        // Guardar dirección
        direccionClienteRepository.save(nuevaDireccion);
        
        log.info("Dirección adicional registrada exitosamente para cliente ID: {}", clienteId);
    }

    /**
     * CASO: Funcionalidad para listar las direcciones adicionales del cliente
     */
    @Transactional(readOnly = true)
    public List<DireccionResponseDto> listarDireccionesCliente(Long clienteId) {
        log.info("Listando direcciones para cliente ID: {}", clienteId);
        
        // Verificar que el cliente existe
        clienteRepository.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con ID: " + clienteId));
        
        // Obtener todas las direcciones del cliente (matriz + adicionales)
        List<DireccionCliente> direcciones = direccionClienteRepository
                .findByClienteIdOrderByEsMatrizDescFechaCreacionAsc(clienteId);
        
        // Convertir a DTOs
        return direcciones.stream() // usamos stream para procesar la lista de direcciones
                .map(this::convertirADireccionResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Método de utilidad: Verificar si cliente tiene dirección matriz
     */
    @Transactional(readOnly = true)
    public boolean clienteTieneDireccionMatriz(Long clienteId) {
        return direccionClienteRepository.existsByClienteIdAndEsMatrizTrue(clienteId);
    }

    /**
     * Método de utilidad: Contar direcciones matriz de un cliente
     */
    @Transactional(readOnly = true)
    public long contarDireccionesMatriz(Long clienteId) {
        return direccionClienteRepository.countByClienteIdAndEsMatrizTrue(clienteId);
    }

    /**
     * Convertir entidad DireccionCliente a DTO de respuesta
     */
    private DireccionResponseDto convertirADireccionResponseDto(DireccionCliente direccion) {
        return DireccionResponseDto.builder()
                .id(direccion.getId())
                .provincia(direccion.getProvincia())
                .ciudad(direccion.getCiudad())
                .direccion(direccion.getDireccion())
                .esMatriz(direccion.getEsMatriz())
                .fechaCreacion(direccion.getFechaCreacion())
                .fechaActualizacion(direccion.getFechaActualizacion())
                .build();
    }
}
