package com.devmobile.viajei.api.model;

import java.io.Serializable;

public class Resposta implements Serializable {

    private boolean Sucesso;
    private String Mensagem;
    private int ChavePrimaria;

    public Resposta() {

    }

    public boolean isSucesso() {
        return Sucesso;
    }

    public void setSucesso(boolean sucesso) {
        Sucesso = sucesso;
    }

    public String getMensagem() {
        return Mensagem;
    }

    public void setMensagem(String mensagem) {
        Mensagem = mensagem;
    }

    public int getChavePrimaria() {
        return ChavePrimaria;
    }

    public void setChavePrimaria(int chavePrimaria) {
        ChavePrimaria = chavePrimaria;
    }
}