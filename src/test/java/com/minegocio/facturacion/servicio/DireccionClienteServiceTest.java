package com.minegocio.facturacion.servicio;

import com.minegocio.facturacion.dto.DireccionCreateRequestDto;
import com.minegocio.facturacion.dto.DireccionResponseDto;
import com.minegocio.facturacion.modelo.Cliente;
import com.minegocio.facturacion.modelo.DireccionCliente;
import com.minegocio.facturacion.repositorio.ClienteRepository;
import com.minegocio.facturacion.repositorio.DireccionClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DireccionClienteServiceTest {

    @Mock
    private DireccionClienteRepository direccionClienteRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private DireccionClienteService direccionClienteService;

    @Test
    void registrarNuevaDireccion_DeberiaGuardarDireccion() {
        // Given
        Cliente cliente = Cliente.builder().id(1L).nombres("Juan").build();
        DireccionCreateRequestDto request = DireccionCreateRequestDto.builder()
                .provincia("Pichincha")
                .ciudad("Quito")
                .direccion("Av. Test")
                .build();

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        // When
        direccionClienteService.registrarNuevaDireccion(1L, request);

        // Then
        verify(direccionClienteRepository).save(any(DireccionCliente.class));
    }

    @Test
    void listarDireccionesCliente_DeberiaRetornarLista() {
        // Given
        Cliente cliente = Cliente.builder().id(1L).nombres("Juan").build();
        DireccionCliente direccion = DireccionCliente.builder()
                .id(1L)
                .provincia("Pichincha")
                .ciudad("Quito")
                .direccion("Av. Test")
                .esMatriz(true)
                .build();

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(direccionClienteRepository.findByClienteIdOrderByEsMatrizDescFechaCreacionAsc(1L))
                .thenReturn(Arrays.asList(direccion));

        // When
        List<DireccionResponseDto> resultado = direccionClienteService.listarDireccionesCliente(1L);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Pichincha", resultado.get(0).getProvincia());
    }
}
