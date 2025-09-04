package com.minegocio.facturacion.controlador;

import com.minegocio.facturacion.dto.ApiResponseDto;
import com.minegocio.facturacion.dto.DireccionCreateRequestDto;
import com.minegocio.facturacion.dto.DireccionResponseDto;
import com.minegocio.facturacion.servicio.DireccionClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
@Slf4j
public class DireccionClienteController {

    private final DireccionClienteService direccionClienteService;

    /**
     * CASO: Funcionalidad para registrar una nueva dirección por cliente
     */
    @PostMapping("/{clienteId}/direcciones")
    public ResponseEntity<ApiResponseDto> registrarNuevaDireccion(
            @PathVariable Long clienteId,
            @Valid @RequestBody DireccionCreateRequestDto request) {
        
        log.info("POST /api/clientes/{}/direcciones - Registrando nueva dirección", clienteId);
        
        try {
            direccionClienteService.registrarNuevaDireccion(clienteId, request);
            
            ApiResponseDto response = ApiResponseDto.success("Dirección registrada exitosamente");
            return ResponseEntity.ok(response);
            
        } catch (IllegalArgumentException e) {
            log.error("Error al registrar dirección: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * CASO: Funcionalidad para listar las direcciones adicionales del cliente
     */
    @GetMapping("/{clienteId}/direcciones")
    public ResponseEntity<List<DireccionResponseDto>> listarDireccionesCliente(
            @PathVariable Long clienteId) {
        
        log.info("GET /api/clientes/{}/direcciones - Listando direcciones del cliente", clienteId);
        
        try {
            List<DireccionResponseDto> direcciones = direccionClienteService.listarDireccionesCliente(clienteId);
            return ResponseEntity.ok(direcciones);
            
        } catch (IllegalArgumentException e) {
            log.error("Error al listar direcciones: {}", e.getMessage());
            throw e;
        }
    }
}
