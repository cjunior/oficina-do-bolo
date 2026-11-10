package com.loja.bolos.service;

import com.loja.bolos.dto.PedidoRequest;
import com.loja.bolos.dto.PedidoResponse;
import com.loja.bolos.entity.Pedido;
import com.loja.bolos.exception.ResourceNotFoundException;
import com.loja.bolos.mapper.PedidoMapper;
import com.loja.bolos.repository.PedidoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock private PedidoRepository pedidoRepository;
    @Mock private PedidoMapper pedidoMapper;

    @InjectMocks private PedidoService pedidoService;

    @Test
    void create_persistsAndReturnsResponse() {
        PedidoRequest request = new PedidoRequest(java.time.LocalDate.now(), "sample", java.math.BigDecimal.ONE, 1L, 1L);
        Pedido entity = new Pedido();
        PedidoResponse response = new PedidoResponse(1L, java.time.LocalDate.now(), "sample", java.math.BigDecimal.ONE, 1L, 1L);

        when(pedidoMapper.toEntity(request)).thenReturn(entity);
        when(pedidoRepository.save(entity)).thenReturn(entity);
        when(pedidoMapper.toResponse(entity)).thenReturn(response);

        PedidoResponse result = pedidoService.create(request);

        assertThat(result).isEqualTo(response);
        verify(pedidoRepository).save(entity);
    }

    @Test
    void findById_whenMissing_throwsNotFound() {
        when(pedidoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> pedidoService.findById(1L))
            .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void delete_whenMissing_throwsNotFound() {
        when(pedidoRepository.existsById(1L)).thenReturn(false);

        assertThatThrownBy(() -> pedidoService.delete(1L))
            .isInstanceOf(ResourceNotFoundException.class);

        verify(pedidoRepository, never()).deleteById(any());
    }

    @Test
    void findAll_returnsMappedResponses() {
        Pedido entity = new Pedido();
        PedidoResponse response = new PedidoResponse(1L, java.time.LocalDate.now(), "sample", java.math.BigDecimal.ONE, 1L, 1L);

        when(pedidoRepository.findAll()).thenReturn(java.util.List.of(entity));
        when(pedidoMapper.toResponse(entity)).thenReturn(response);

        assertThat(pedidoService.findAll()).containsExactly(response);
    }

    @Test
    void findById_whenPresent_returnsResponse() {
        Pedido entity = new Pedido();
        PedidoResponse response = new PedidoResponse(1L, java.time.LocalDate.now(), "sample", java.math.BigDecimal.ONE, 1L, 1L);

        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(pedidoMapper.toResponse(entity)).thenReturn(response);

        assertThat(pedidoService.findById(1L)).isEqualTo(response);
    }

    @Test
    void update_whenPresent_updatesAndReturnsResponse() {
        PedidoRequest request = new PedidoRequest(java.time.LocalDate.now(), "sample", java.math.BigDecimal.ONE, 1L, 1L);
        Pedido entity = new Pedido();
        PedidoResponse response = new PedidoResponse(1L, java.time.LocalDate.now(), "sample", java.math.BigDecimal.ONE, 1L, 1L);

        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(pedidoMapper.toResponse(entity)).thenReturn(response);

        assertThat(pedidoService.update(1L, request)).isEqualTo(response);
        verify(pedidoMapper).updateEntity(entity, request);
    }

    @Test
    void delete_whenPresent_deletesById() {
        when(pedidoRepository.existsById(1L)).thenReturn(true);

        pedidoService.delete(1L);

        verify(pedidoRepository).deleteById(1L);
    }
}
