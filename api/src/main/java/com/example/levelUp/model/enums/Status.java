package com.example.levelUp.model.enums;

public enum Status {
    PENDENTE("Pendente"),
    EM_ANDAMENTO("Em andamento"),
    CONCLUIDA("Concluida"),
    CANCELADA("Cancelada"),
    ATRASADA("Atrasada");

    private String texto;

    Status(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }
}
