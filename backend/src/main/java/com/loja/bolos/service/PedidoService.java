package com.loja.bolos.service;

import com.loja.bolos.dto.PedidoRequest;
import com.loja.bolos.dto.PedidoResponse;
import com.loja.bolos.entity.Pedido;
import com.loja.bolos.exception.ResourceNotFoundException;
import com.loja.bolos.mapper.PedidoMapper;
import com.loja.bolos.repository.PedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final PedidoMapper pedidoMapper;

    public PedidoService(PedidoRepository pedidoRepository,
                                PedidoMapper pedidoMapper) {
        this.pedidoRepository = pedidoRepository;
        this.pedidoMapper = pedidoMapper;
    }

    @Transactional(readOnly = true)
    public List<PedidoResponse> findAll() {
        return pedidoRepository.findAll().stream()
            .map(pedidoMapper::toResponse)
            .toList();
    }

    @Transactional(readOnly = true)
    public PedidoResponse findById(Long id) {
        Pedido entity = pedidoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Pedido not found: " + id));
        return pedidoMapper.toResponse(entity);
    }

    @Transactional
    public PedidoResponse create(PedidoRequest request) {
        Pedido entity = pedidoMapper.toEntity(request);
        Pedido saved = pedidoRepository.save(entity);
        return pedidoMapper.toResponse(saved);
    }

    @Transactional
    public PedidoResponse update(Long id, PedidoRequest request) {
        Pedido entity = pedidoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Pedido not found: " + id));
        pedidoMapper.updateEntity(entity, request);
        return pedidoMapper.toResponse(entity);
    }

    @Transactional
    public void delete(Long id) {
        if (!pedidoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Pedido not found: " + id);
        }
        pedidoRepository.deleteById(id);
    }
}
