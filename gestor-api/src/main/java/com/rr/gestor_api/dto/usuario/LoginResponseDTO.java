package com.rr.gestor_api.dto.usuario;

import java.util.UUID;

import com.rr.gestor_api.domain.usuario.TiposUsuarios;

public record LoginResponseDTO(UUID id, String name, String token, TiposUsuarios tipo) {
}
