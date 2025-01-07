package com.rr.gestor_api.controllers;

import com.rr.gestor_api.domain.usuario.Usuario;
import com.rr.gestor_api.dto.usuario.*;
import com.rr.gestor_api.infra.security.TokenService;
import com.rr.gestor_api.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO body) {
        try {
            Usuario user = this.repository.findByEmail(body.email())
                    .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
            if (passwordEncoder.matches(body.senha(), user.getSenha())) {
                String token = this.tokenService.generateToken(user);
                return ResponseEntity.ok(new LoginResponseDTO(user.getNome(), token));
            }
            return ResponseEntity.badRequest().body("Usuário ou senha incorreto");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário ou senha incorreto");
        }
    }

    @PostMapping("/register")
    public ResponseEntity login(@RequestBody RegisterRequestDTO body) {
        Optional<Usuario> user = this.repository.findByEmail(body.email());
        if (user.isEmpty()) {
            Usuario newUser = new Usuario();
            newUser.setSenha(passwordEncoder.encode(body.senha()));
            newUser.setEmail(body.email());
            newUser.setNome(body.nome());
            newUser.setTipo(body.tipo());
            repository.save(newUser);
            String token = this.tokenService.generateToken(newUser);
            return ResponseEntity.ok(new LoginResponseDTO(newUser.getNome(), token));

        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/atualizarSenha")
    public ResponseEntity atualizar(@RequestBody atualizarSenhaDTO body) {
        Usuario usuario = repository.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuario.setSenha(passwordEncoder.encode(body.senhaNova()));
        repository.save(usuario);
        return ResponseEntity.ok("Sucesso!");
    }
}
