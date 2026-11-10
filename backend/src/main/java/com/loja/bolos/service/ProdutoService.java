package com.loja.bolos.service;

import com.loja.bolos.dto.ProdutoRequest;
import com.loja.bolos.dto.ProdutoResponse;
import com.loja.bolos.entity.Produto;
import com.loja.bolos.exception.ResourceNotFoundException;
import com.loja.bolos.mapper.ProdutoMapper;
import com.loja.bolos.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ProdutoMapper produtoMapper;

    public ProdutoService(ProdutoRepository produtoRepository,
                                ProdutoMapper produtoMapper) {
        this.produtoRepository = produtoRepository;
        this.produtoMapper = produtoMapper;
    }

    @Transactional(readOnly = true)
    public List<ProdutoResponse> findAll() {
        return produtoRepository.findAll().stream()
            .map(produtoMapper::toResponse)
            .toList();
    }

    @Transactional(readOnly = true)
    public ProdutoResponse findById(Long id) {
        Produto entity = produtoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Produto not found: " + id));
        return produtoMapper.toResponse(entity);
    }

    @Transactional
    public ProdutoResponse create(ProdutoRequest request) {
        Produto entity = produtoMapper.toEntity(request);
        Produto saved = produtoRepository.save(entity);
        return produtoMapper.toResponse(saved);
    }

    @Transactional
    public ProdutoResponse update(Long id, ProdutoRequest request) {
        Produto entity = produtoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Produto not found: " + id));
        produtoMapper.updateEntity(entity, request);
        return produtoMapper.toResponse(entity);
    }

    @Transactional
    public void delete(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Produto not found: " + id);
        }
        produtoRepository.deleteById(id);
    }
}
