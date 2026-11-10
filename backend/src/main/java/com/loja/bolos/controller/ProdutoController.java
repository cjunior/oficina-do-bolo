package com.loja.bolos.controller;

import com.loja.bolos.dto.ProdutoRequest;
import com.loja.bolos.dto.ProdutoResponse;
import com.loja.bolos.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Tag(name = "Produto", description = "CRUD operations for Produto")
@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @Operation(summary = "List all Produto records")
    @GetMapping
    public List<ProdutoResponse> findAll() {
        return produtoService.findAll();
    }

    @Operation(summary = "Get a Produto by id")
    @GetMapping("/{id}")
    public ProdutoResponse findById(@PathVariable Long id) {
        return produtoService.findById(id);
    }

    @Operation(summary = "Create a new Produto")
    @PostMapping
    public ResponseEntity<ProdutoResponse> create(@Valid @RequestBody ProdutoRequest request) {
        ProdutoResponse created = produtoService.create(request);
        return ResponseEntity
            .created(URI.create("/api/produtos/" + created.id()))
            .body(created);
    }

    @Operation(summary = "Update an existing Produto")
    @PutMapping("/{id}")
    public ProdutoResponse update(@PathVariable Long id,
                                        @Valid @RequestBody ProdutoRequest request) {
        return produtoService.update(id, request);
    }

    @Operation(summary = "Delete a Produto by id")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        produtoService.delete(id);
    }
}
