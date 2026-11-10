package com.loja.bolos.controller;

import com.loja.bolos.dto.ClienteRequest;
import com.loja.bolos.dto.ClienteResponse;
import com.loja.bolos.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Tag(name = "Cliente", description = "CRUD operations for Cliente")
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @Operation(summary = "List all Cliente records")
    @GetMapping
    public List<ClienteResponse> findAll() {
        return clienteService.findAll();
    }

    @Operation(summary = "Get a Cliente by id")
    @GetMapping("/{id}")
    public ClienteResponse findById(@PathVariable Long id) {
        return clienteService.findById(id);
    }

    @Operation(summary = "Create a new Cliente")
    @PostMapping
    public ResponseEntity<ClienteResponse> create(@Valid @RequestBody ClienteRequest request) {
        ClienteResponse created = clienteService.create(request);
        return ResponseEntity
            .created(URI.create("/api/clientes/" + created.id()))
            .body(created);
    }

    @Operation(summary = "Update an existing Cliente")
    @PutMapping("/{id}")
    public ClienteResponse update(@PathVariable Long id,
                                        @Valid @RequestBody ClienteRequest request) {
        return clienteService.update(id, request);
    }

    @Operation(summary = "Delete a Cliente by id")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        clienteService.delete(id);
    }
}
