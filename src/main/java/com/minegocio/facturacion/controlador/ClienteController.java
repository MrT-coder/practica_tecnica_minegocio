package com.minegocio.facturacion.controlador;

import com.minegocio.facturacion.dto.*;
import com.minegocio.facturacion.servicio.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
@Slf4j
public class ClienteController {

    private final ClienteService clienteService;

    /**
     * CASO: Funcionalidad para buscar y obtener un listado de clientes
     */
    @GetMapping
    public ResponseEntity<List<ClienteResponseDto>> buscarClientes(
            @RequestParam(value = "criterio", required = false) String criterio) {
        
        log.info("GET /api/clientes - Búsqueda con criterio: {}", criterio);
        
        List<ClienteResponseDto> clientes = clienteService.buscarClientes(criterio);
        
        return ResponseEntity.ok(clientes);
    }


    /**
     * CASO: Funcionalidad para crear un nuevo cliente con la dirección matriz
     */
    @PostMapping
    public ResponseEntity<ClienteResponseDto> crearCliente(
            @Valid @RequestBody ClienteCreateRequestDto request) {
        
        log.info("POST /api/clientes - Creando cliente: {}", request.getNumeroIdentificacion());
        
        try {
            ClienteResponseDto clienteCreado = clienteService.crearCliente(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(clienteCreado);
            
        } catch (IllegalArgumentException e) {
            log.error("Error al crear cliente: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * CASO: Funcionalidad para editar los datos del cliente
     */
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDto> editarCliente(
            @PathVariable Long id,
            @Valid @RequestBody ClienteUpdateRequestDto request) {
        
        log.info("PUT /api/clientes/{} - Editando cliente", id);
        
        try {
            ClienteResponseDto clienteActualizado = clienteService.editarCliente(id, request);
            return ResponseEntity.ok(clienteActualizado);
            
        } catch (IllegalArgumentException e) {
            log.error("Error al editar cliente: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * CASO: Funcionalidad para eliminar un cliente
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto> eliminarCliente(@PathVariable Long id) {
        
        log.info("DELETE /api/clientes/{} - Eliminando cliente", id);
        
        try {
            clienteService.eliminarCliente(id);
            
            ApiResponseDto response = ApiResponseDto.success("Cliente eliminado exitosamente");
            return ResponseEntity.ok(response);
            
        } catch (IllegalArgumentException e) {
            log.error("Error al eliminar cliente: {}", e.getMessage());
            throw e;
        }
    }

}
