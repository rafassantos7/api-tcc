package com.example.levelUp.model.enums;

public enum Prioridade {
    BAIXA("Baixa"),
    MEDIA("Media"),
    ALTA("Alta");

    private String texto;

    Prioridade(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }

}
