package com.loja.bolos.service;

import com.loja.bolos.dto.UsuarioRequest;
import com.loja.bolos.dto.UsuarioResponse;
import com.loja.bolos.entity.Usuario;
import com.loja.bolos.exception.ResourceNotFoundException;
import com.loja.bolos.mapper.UsuarioMapper;
import com.loja.bolos.repository.UsuarioRepository;
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
class UsuarioServiceTest {

    @Mock private UsuarioRepository usuarioRepository;
    @Mock private UsuarioMapper usuarioMapper;

    @InjectMocks private UsuarioService usuarioService;

    @Test
    void create_persistsAndReturnsResponse() {
        UsuarioRequest request = new UsuarioRequest("sample", "user@example.com", "sample", "sample");
        Usuario entity = new Usuario();
        UsuarioResponse response = new UsuarioResponse(1L, "sample", "user@example.com", "sample", "sample");

        when(usuarioMapper.toEntity(request)).thenReturn(entity);
        when(usuarioRepository.save(entity)).thenReturn(entity);
        when(usuarioMapper.toResponse(entity)).thenReturn(response);

        UsuarioResponse result = usuarioService.create(request);

        assertThat(result).isEqualTo(response);
        verify(usuarioRepository).save(entity);
    }

    @Test
    void findById_whenMissing_throwsNotFound() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> usuarioService.findById(1L))
            .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void delete_whenMissing_throwsNotFound() {
        when(usuarioRepository.existsById(1L)).thenReturn(false);

        assertThatThrownBy(() -> usuarioService.delete(1L))
            .isInstanceOf(ResourceNotFoundException.class);

        verify(usuarioRepository, never()).deleteById(any());
    }

    @Test
    void findAll_returnsMappedResponses() {
        Usuario entity = new Usuario();
        UsuarioResponse response = new UsuarioResponse(1L, "sample", "user@example.com", "sample", "sample");

        when(usuarioRepository.findAll()).thenReturn(java.util.List.of(entity));
        when(usuarioMapper.toResponse(entity)).thenReturn(response);

        assertThat(usuarioService.findAll()).containsExactly(response);
    }

    @Test
    void findById_whenPresent_returnsResponse() {
        Usuario entity = new Usuario();
        UsuarioResponse response = new UsuarioResponse(1L, "sample", "user@example.com", "sample", "sample");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(usuarioMapper.toResponse(entity)).thenReturn(response);

        assertThat(usuarioService.findById(1L)).isEqualTo(response);
    }

    @Test
    void update_whenPresent_updatesAndReturnsResponse() {
        UsuarioRequest request = new UsuarioRequest("sample", "user@example.com", "sample", "sample");
        Usuario entity = new Usuario();
        UsuarioResponse response = new UsuarioResponse(1L, "sample", "user@example.com", "sample", "sample");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(usuarioMapper.toResponse(entity)).thenReturn(response);

        assertThat(usuarioService.update(1L, request)).isEqualTo(response);
        verify(usuarioMapper).updateEntity(entity, request);
    }

    @Test
    void delete_whenPresent_deletesById() {
        when(usuarioRepository.existsById(1L)).thenReturn(true);

        usuarioService.delete(1L);

        verify(usuarioRepository).deleteById(1L);
    }
}
