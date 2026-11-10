package com.loja.bolos.mapper;

import com.loja.bolos.dto.ProdutoRequest;
import com.loja.bolos.dto.ProdutoResponse;
import com.loja.bolos.entity.Produto;
import org.springframework.stereotype.Component;

@Component
public class ProdutoMapper {

    public Produto toEntity(ProdutoRequest request) {
        Produto entity = new Produto();
        entity.setNome(request.nome());
        entity.setDescricao(request.descricao());
        entity.setPreco(request.preco());
        entity.setImagem(request.imagem());
        entity.setAtivo(request.ativo());
        return entity;
    }

    public ProdutoResponse toResponse(Produto entity) {
        return new ProdutoResponse(
            entity.getId(),
            entity.getNome(),
            entity.getDescricao(),
            entity.getPreco(),
            entity.getImagem(),
            entity.getAtivo()
        );
    }

    public void updateEntity(Produto entity, ProdutoRequest request) {
        entity.setNome(request.nome());
        entity.setDescricao(request.descricao());
        entity.setPreco(request.preco());
        entity.setImagem(request.imagem());
        entity.setAtivo(request.ativo());
    }
}
