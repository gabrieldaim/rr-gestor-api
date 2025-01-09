package com.rr.gestor_api.service.erro;

public class ErroException extends RuntimeException {

    private final String campo;

    public ErroException(String campo, String mensagem) {
        super(mensagem);
        this.campo = campo;
    }

    public String getCampo() {
        return campo;
    }
}

