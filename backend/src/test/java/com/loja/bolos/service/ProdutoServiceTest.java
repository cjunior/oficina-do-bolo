package com.loja.bolos.service;

import com.loja.bolos.dto.ProdutoRequest;
import com.loja.bolos.dto.ProdutoResponse;
import com.loja.bolos.entity.Produto;
import com.loja.bolos.exception.ResourceNotFoundException;
import com.loja.bolos.mapper.ProdutoMapper;
import com.loja.bolos.repository.ProdutoRepository;
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
class ProdutoServiceTest {

    @Mock private ProdutoRepository produtoRepository;
    @Mock private ProdutoMapper produtoMapper;

    @InjectMocks private ProdutoService produtoService;

    @Test
    void create_persistsAndReturnsResponse() {
        ProdutoRequest request = new ProdutoRequest("sample", "sample", java.math.BigDecimal.ONE, new byte[0], true);
        Produto entity = new Produto();
        ProdutoResponse response = new ProdutoResponse(1L, "sample", "sample", java.math.BigDecimal.ONE, new byte[0], true);

        when(produtoMapper.toEntity(request)).thenReturn(entity);
        when(produtoRepository.save(entity)).thenReturn(entity);
        when(produtoMapper.toResponse(entity)).thenReturn(response);

        ProdutoResponse result = produtoService.create(request);

        assertThat(result).isEqualTo(response);
        verify(produtoRepository).save(entity);
    }

    @Test
    void findById_whenMissing_throwsNotFound() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> produtoService.findById(1L))
            .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void delete_whenMissing_throwsNotFound() {
        when(produtoRepository.existsById(1L)).thenReturn(false);

        assertThatThrownBy(() -> produtoService.delete(1L))
            .isInstanceOf(ResourceNotFoundException.class);

        verify(produtoRepository, never()).deleteById(any());
    }

    @Test
    void findAll_returnsMappedResponses() {
        Produto entity = new Produto();
        ProdutoResponse response = new ProdutoResponse(1L, "sample", "sample", java.math.BigDecimal.ONE, new byte[0], true);

        when(produtoRepository.findAll()).thenReturn(java.util.List.of(entity));
        when(produtoMapper.toResponse(entity)).thenReturn(response);

        assertThat(produtoService.findAll()).containsExactly(response);
    }

    @Test
    void findById_whenPresent_returnsResponse() {
        Produto entity = new Produto();
        ProdutoResponse response = new ProdutoResponse(1L, "sample", "sample", java.math.BigDecimal.ONE, new byte[0], true);

        when(produtoRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(produtoMapper.toResponse(entity)).thenReturn(response);

        assertThat(produtoService.findById(1L)).isEqualTo(response);
    }

    @Test
    void update_whenPresent_updatesAndReturnsResponse() {
        ProdutoRequest request = new ProdutoRequest("sample", "sample", java.math.BigDecimal.ONE, new byte[0], true);
        Produto entity = new Produto();
        ProdutoResponse response = new ProdutoResponse(1L, "sample", "sample", java.math.BigDecimal.ONE, new byte[0], true);

        when(produtoRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(produtoMapper.toResponse(entity)).thenReturn(response);

        assertThat(produtoService.update(1L, request)).isEqualTo(response);
        verify(produtoMapper).updateEntity(entity, request);
    }

    @Test
    void delete_whenPresent_deletesById() {
        when(produtoRepository.existsById(1L)).thenReturn(true);

        produtoService.delete(1L);

        verify(produtoRepository).deleteById(1L);
    }
}
