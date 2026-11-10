package com.loja.bolos.service;

import com.loja.bolos.dto.ClienteRequest;
import com.loja.bolos.dto.ClienteResponse;
import com.loja.bolos.entity.Cliente;
import com.loja.bolos.exception.ResourceNotFoundException;
import com.loja.bolos.mapper.ClienteMapper;
import com.loja.bolos.repository.ClienteRepository;
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
class ClienteServiceTest {

    @Mock private ClienteRepository clienteRepository;
    @Mock private ClienteMapper clienteMapper;

    @InjectMocks private ClienteService clienteService;

    @Test
    void create_persistsAndReturnsResponse() {
        ClienteRequest request = new ClienteRequest("sample", "user@example.com", "sample");
        Cliente entity = new Cliente();
        ClienteResponse response = new ClienteResponse(1L, "sample", "user@example.com", "sample");

        when(clienteMapper.toEntity(request)).thenReturn(entity);
        when(clienteRepository.save(entity)).thenReturn(entity);
        when(clienteMapper.toResponse(entity)).thenReturn(response);

        ClienteResponse result = clienteService.create(request);

        assertThat(result).isEqualTo(response);
        verify(clienteRepository).save(entity);
    }

    @Test
    void findById_whenMissing_throwsNotFound() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> clienteService.findById(1L))
            .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void delete_whenMissing_throwsNotFound() {
        when(clienteRepository.existsById(1L)).thenReturn(false);

        assertThatThrownBy(() -> clienteService.delete(1L))
            .isInstanceOf(ResourceNotFoundException.class);

        verify(clienteRepository, never()).deleteById(any());
    }

    @Test
    void findAll_returnsMappedResponses() {
        Cliente entity = new Cliente();
        ClienteResponse response = new ClienteResponse(1L, "sample", "user@example.com", "sample");

        when(clienteRepository.findAll()).thenReturn(java.util.List.of(entity));
        when(clienteMapper.toResponse(entity)).thenReturn(response);

        assertThat(clienteService.findAll()).containsExactly(response);
    }

    @Test
    void findById_whenPresent_returnsResponse() {
        Cliente entity = new Cliente();
        ClienteResponse response = new ClienteResponse(1L, "sample", "user@example.com", "sample");

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(clienteMapper.toResponse(entity)).thenReturn(response);

        assertThat(clienteService.findById(1L)).isEqualTo(response);
    }

    @Test
    void update_whenPresent_updatesAndReturnsResponse() {
        ClienteRequest request = new ClienteRequest("sample", "user@example.com", "sample");
        Cliente entity = new Cliente();
        ClienteResponse response = new ClienteResponse(1L, "sample", "user@example.com", "sample");

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(clienteMapper.toResponse(entity)).thenReturn(response);

        assertThat(clienteService.update(1L, request)).isEqualTo(response);
        verify(clienteMapper).updateEntity(entity, request);
    }

    @Test
    void delete_whenPresent_deletesById() {
        when(clienteRepository.existsById(1L)).thenReturn(true);

        clienteService.delete(1L);

        verify(clienteRepository).deleteById(1L);
    }
}
