package com.rr.gestor_api.dto.usuario;

import java.util.UUID;

public record LoginResponseDTO(UUID id, String name, String token){
}
