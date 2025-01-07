package com.rr.gestor_api.dto.usuario;

import com.rr.gestor_api.domain.usuario.TiposUsuarios;

public record RegisterRequestDTO(String nome, String email, String senha, TiposUsuarios tipo){
}
