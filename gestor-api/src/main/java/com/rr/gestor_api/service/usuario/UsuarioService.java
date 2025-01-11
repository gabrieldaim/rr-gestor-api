package com.rr.gestor_api.service.usuario;

import com.rr.gestor_api.domain.usuario.TiposUsuarios;
import com.rr.gestor_api.domain.usuario.Usuario;
import com.rr.gestor_api.infra.security.CustomUserDetailsService;
import com.rr.gestor_api.service.erro.ErroException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UsuarioService {

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    public Usuario capturaUsuarioToken(){
        Usuario currentUser = customUserDetailsService.getCurrentUser();
        if (currentUser == null) {
            throw new ErroException("login","token não autorizado");
        }

        return currentUser;
    }

    public Boolean userIsAuthorized(){
        if(TiposUsuarios.ADMIN.equals(capturaUsuarioToken().getTipo())){
            return true;
        }
        else{
            throw new ErroException("token", "Usuário não autorizado a realizar essa ação");
        }
    }

    public Boolean usuarioIsResponsavel(UUID usuarioResponsavel){
        if(usuarioResponsavel.equals(capturaUsuarioToken().getId()) || TiposUsuarios.ADMIN.equals(capturaUsuarioToken().getTipo())){
            return true;
        }
        else{
            throw new ErroException("token", "Usuário não autorizado a realizar essa ação");
        }
    }

}
