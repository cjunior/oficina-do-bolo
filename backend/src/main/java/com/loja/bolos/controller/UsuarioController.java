package com.loja.bolos.controller;

import com.loja.bolos.dto.UsuarioRequest;
import com.loja.bolos.dto.UsuarioResponse;
import com.loja.bolos.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Tag(name = "Usuario", description = "CRUD operations for Usuario")
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Operation(summary = "List all Usuario records")
    @GetMapping
    public List<UsuarioResponse> findAll() {
        return usuarioService.findAll();
    }

    @Operation(summary = "Get a Usuario by id")
    @GetMapping("/{id}")
    public UsuarioResponse findById(@PathVariable Long id) {
        return usuarioService.findById(id);
    }

    @Operation(summary = "Create a new Usuario")
    @PostMapping
    public ResponseEntity<UsuarioResponse> create(@Valid @RequestBody UsuarioRequest request) {
        UsuarioResponse created = usuarioService.create(request);
        return ResponseEntity
            .created(URI.create("/api/usuarios/" + created.id()))
            .body(created);
    }

    @Operation(summary = "Update an existing Usuario")
    @PutMapping("/{id}")
    public UsuarioResponse update(@PathVariable Long id,
                                        @Valid @RequestBody UsuarioRequest request) {
        return usuarioService.update(id, request);
    }

    @Operation(summary = "Delete a Usuario by id")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        usuarioService.delete(id);
    }
}
