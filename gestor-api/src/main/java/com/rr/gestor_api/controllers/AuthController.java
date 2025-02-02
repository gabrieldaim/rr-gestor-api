package com.rr.gestor_api.controllers;

import com.rr.gestor_api.domain.usuario.Usuario;
import com.rr.gestor_api.dto.erro.ErroDTO;
import com.rr.gestor_api.dto.usuario.*;
import com.rr.gestor_api.infra.security.TokenService;
import com.rr.gestor_api.repositories.UsuarioRepository;
import com.rr.gestor_api.service.erro.ErroException;
import com.rr.gestor_api.service.usuario.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO body) {
        try {
            Usuario user = this.repository.findByEmail(body.email())
                    .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
            if (passwordEncoder.matches(body.senha(), user.getSenha())) {
                String token = this.tokenService.generateToken(user);
                return ResponseEntity.ok(new LoginResponseDTO(user.getId(),user.getNome(), token,user.getTipo()));
            }
            return ResponseEntity.badRequest().body("Usuário ou senha incorreto");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário ou senha incorreto");
        }
    }

    @PostMapping("/register")
    public ResponseEntity login(@RequestBody RegisterRequestDTO body) {
        try{
            // usuarioService.userIsAuthorized();
            Optional<Usuario> user = this.repository.findByEmail(body.email());
            if (user.isEmpty()) {
                Usuario newUser = new Usuario();
                newUser.setSenha(passwordEncoder.encode(body.senha()));
                newUser.setEmail(body.email());
                newUser.setNome(body.nome());
                newUser.setTipo(body.tipo());
                repository.save(newUser);
                String token = this.tokenService.generateToken(newUser);
                return ResponseEntity.ok(new LoginResponseDTO(newUser.getId(),newUser.getNome(), token,newUser.getTipo()));

            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já cadastrado no sistema");
        }catch (ErroException e) {
            // Caso o trabalho não seja encontrado, retorna erro
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErroDTO(e.getCampo(),e.getMessage()));
        }
    }

    @PostMapping("/atualizarSenha")
    public ResponseEntity atualizar(@RequestBody atualizarSenhaDTO body) {
        Usuario usuario = repository.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuario.setSenha(passwordEncoder.encode(body.senhaNova()));
        repository.save(usuario);
        return ResponseEntity.ok("Sucesso!");
    }

    @GetMapping("/listar")
    public ResponseEntity<List<UsuarioResumoRetornoDTO>> listar() {
        List<Usuario> usuarios = repository.findAll();
        return ResponseEntity.ok(usuarios.stream().map(UsuarioResumoRetornoDTO::new).toList());
    }

}
