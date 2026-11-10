package com.loja.bolos.service;

import com.loja.bolos.dto.UsuarioRequest;
import com.loja.bolos.dto.UsuarioResponse;
import com.loja.bolos.entity.Usuario;
import com.loja.bolos.exception.ResourceNotFoundException;
import com.loja.bolos.mapper.UsuarioMapper;
import com.loja.bolos.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public UsuarioService(UsuarioRepository usuarioRepository,
                                UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponse> findAll() {
        return usuarioRepository.findAll().stream()
            .map(usuarioMapper::toResponse)
            .toList();
    }

    @Transactional(readOnly = true)
    public UsuarioResponse findById(Long id) {
        Usuario entity = usuarioRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Usuario not found: " + id));
        return usuarioMapper.toResponse(entity);
    }

    @Transactional
    public UsuarioResponse create(UsuarioRequest request) {
        Usuario entity = usuarioMapper.toEntity(request);
        Usuario saved = usuarioRepository.save(entity);
        return usuarioMapper.toResponse(saved);
    }

    @Transactional
    public UsuarioResponse update(Long id, UsuarioRequest request) {
        Usuario entity = usuarioRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Usuario not found: " + id));
        usuarioMapper.updateEntity(entity, request);
        return usuarioMapper.toResponse(entity);
    }

    @Transactional
    public void delete(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuario not found: " + id);
        }
        usuarioRepository.deleteById(id);
    }
}
