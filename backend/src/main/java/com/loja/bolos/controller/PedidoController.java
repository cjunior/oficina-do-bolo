package com.loja.bolos.controller;

import com.loja.bolos.dto.PedidoRequest;
import com.loja.bolos.dto.PedidoResponse;
import com.loja.bolos.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Tag(name = "Pedido", description = "CRUD operations for Pedido")
@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @Operation(summary = "List all Pedido records")
    @GetMapping
    public List<PedidoResponse> findAll() {
        return pedidoService.findAll();
    }

    @Operation(summary = "Get a Pedido by id")
    @GetMapping("/{id}")
    public PedidoResponse findById(@PathVariable Long id) {
        return pedidoService.findById(id);
    }

    @Operation(summary = "Create a new Pedido")
    @PostMapping
    public ResponseEntity<PedidoResponse> create(@Valid @RequestBody PedidoRequest request) {
        PedidoResponse created = pedidoService.create(request);
        return ResponseEntity
            .created(URI.create("/api/pedidos/" + created.id()))
            .body(created);
    }

    @Operation(summary = "Update an existing Pedido")
    @PutMapping("/{id}")
    public PedidoResponse update(@PathVariable Long id,
                                        @Valid @RequestBody PedidoRequest request) {
        return pedidoService.update(id, request);
    }

    @Operation(summary = "Delete a Pedido by id")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        pedidoService.delete(id);
    }
}
