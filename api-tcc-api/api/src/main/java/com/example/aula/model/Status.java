package com.example.aula.model;

public enum Status {
    PENDENTE("Pendente"),
    EM_ANDAMENTO("Em andamento"),
    CONCLUIDA("Concluida"),
    ATRASADA("Atrasada"),
    CANCELADA("Cancalada");


    private String texto;

    Status(String texto) {
        this.texto = texto;
    }


    public String getTexto() {
        return texto;
    }
}
