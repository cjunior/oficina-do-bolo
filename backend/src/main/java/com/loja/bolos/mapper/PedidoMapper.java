package com.loja.bolos.mapper;

import com.loja.bolos.dto.PedidoRequest;
import com.loja.bolos.dto.PedidoResponse;
import com.loja.bolos.entity.Pedido;
import com.loja.bolos.repository.ClienteRepository;
import com.loja.bolos.repository.ProdutoRepository;
import org.springframework.stereotype.Component;

@Component
public class PedidoMapper {

    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;

    public PedidoMapper(ClienteRepository clienteRepository, ProdutoRepository produtoRepository) {
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
    }

    public Pedido toEntity(PedidoRequest request) {
        Pedido entity = new Pedido();
        entity.setDataPedido(request.dataPedido());
        entity.setStatus(request.status());
        entity.setValorTotal(request.valorTotal());
        if (request.clienteId() != null) {
            entity.setCliente(clienteRepository.getReferenceById(request.clienteId()));
        }
        if (request.produtosId() != null) {
            entity.setProdutos(produtoRepository.getReferenceById(request.produtosId()));
        }
        return entity;
    }

    public PedidoResponse toResponse(Pedido entity) {
        return new PedidoResponse(
            entity.getId(),
            entity.getDataPedido(),
            entity.getStatus(),
            entity.getValorTotal(),
            entity.getCliente() != null ? entity.getCliente().getId() : null,
            entity.getProdutos() != null ? entity.getProdutos().getId() : null
        );
    }

    public void updateEntity(Pedido entity, PedidoRequest request) {
        entity.setDataPedido(request.dataPedido());
        entity.setStatus(request.status());
        entity.setValorTotal(request.valorTotal());
        if (request.clienteId() != null) {
            entity.setCliente(clienteRepository.getReferenceById(request.clienteId()));
        }
        if (request.produtosId() != null) {
            entity.setProdutos(produtoRepository.getReferenceById(request.produtosId()));
        }
    }
}
