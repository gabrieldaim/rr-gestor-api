package com.rr.gestor_api.dto.usuario;

import com.rr.gestor_api.domain.usuario.Usuario;

import java.util.UUID;

public record UsuarioResumoRetornoDTO(UUID id,
                                      String nome,
                                      String email) {
    public UsuarioResumoRetornoDTO(Usuario usuario){
        this(usuario.getId(),usuario.getNome(),usuario.getEmail());
    }
}
