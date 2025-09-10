package com.minegocio.facturacion.servicio;

import com.minegocio.facturacion.dto.*;
import com.minegocio.facturacion.modelo.Cliente;
import com.minegocio.facturacion.modelo.TipoIdentificacion;
import com.minegocio.facturacion.repositorio.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    void crearCliente_DeberiaGuardarCliente() {
        // Given - Preparar
        DireccionCreateRequestDto direccionRequest = DireccionCreateRequestDto.builder()
                .provincia("Pichincha")
                .ciudad("Quito")
                .direccion("Av. 10 de Agosto")
                .build();

        ClienteCreateRequestDto request = ClienteCreateRequestDto.builder()
                .tipoIdentificacion("CEDULA")
                .numeroIdentificacion("1234567890")
                .nombres("Juan Pérez")
                .correo("juan@email.com")
                .numeroCelular("0987654321")
                .direccionMatriz(direccionRequest)
                .build();

        Cliente clienteGuardado = Cliente.builder()
                .id(1L)
                .tipoIdentificacion(TipoIdentificacion.CEDULA)
                .numeroIdentificacion("1234567890")
                .nombres("Juan Pérez")
                .correo("juan@email.com")
                .numeroCelular("0987654321")
                .build();

        when(clienteRepository.existsByNumeroIdentificacion("1234567890")).thenReturn(false);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteGuardado);

        // When - Ejecutar
        ClienteResponseDto resultado = clienteService.crearCliente(request);

        // Then - Verificar
        assertNotNull(resultado);
        assertEquals("Juan Pérez", resultado.getNombres());
        verify(clienteRepository).save(any(Cliente.class));
    }

    @Test
    void crearCliente_ConNumeroExistente_DeberiaLanzarExcepcion() {
        // Given
        DireccionCreateRequestDto direccionRequest = DireccionCreateRequestDto.builder()
                .provincia("Pichincha")
                .ciudad("Quito")
                .direccion("Av. 10 de Agosto")
                .build();

        ClienteCreateRequestDto request = ClienteCreateRequestDto.builder()
                .tipoIdentificacion("CEDULA")
                .numeroIdentificacion("1234567890")
                .nombres("Juan Pérez")
                .correo("juan@email.com")
                .numeroCelular("0987654321")
                .direccionMatriz(direccionRequest)
                .build();

        when(clienteRepository.existsByNumeroIdentificacion("1234567890")).thenReturn(true);

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> clienteService.crearCliente(request));
        verify(clienteRepository, never()).save(any());
    }

    @Test
    void buscarClientes_DeberiaRetornarLista() {
        // Given
        Cliente cliente = Cliente.builder()
                .id(1L)
                .tipoIdentificacion(TipoIdentificacion.CEDULA)
                .numeroIdentificacion("1234567890")
                .nombres("Juan Pérez")
                .correo("juan@email.com")
                .numeroCelular("0987654321")
                .build();

        when(clienteRepository.findByNumeroIdentificacion("Juan")).thenReturn(Optional.empty());
        when(clienteRepository.findByNumeroIdentificacionContaining("Juan")).thenReturn(Arrays.asList());
        when(clienteRepository.findByNombresContainingIgnoreCase("Juan")).thenReturn(Arrays.asList(cliente));

        // When
        List<ClienteResponseDto> resultado = clienteService.buscarClientes("Juan");

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Juan Pérez", resultado.get(0).getNombres());
    }

    @Test
    void eliminarCliente_DeberiaEliminar() {
        // Given
        Cliente cliente = Cliente.builder()
                .id(1L)
                .nombres("Juan Pérez")
                .build();

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        // When
        clienteService.eliminarCliente(1L);

        // Then
        verify(clienteRepository).deleteById(1L);
    }
}
