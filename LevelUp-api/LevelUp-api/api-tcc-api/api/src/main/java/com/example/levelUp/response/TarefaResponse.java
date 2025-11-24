package com.example.levelUp.response;

public class TarefaResponse {
    private Long id;
    private String titulo;
    private String descricao;
    private String dataInicio;
    private String dataConclusao;
    private String status;
    private String prioridade;


    public TarefaResponse() {
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getTitulo() {
        return titulo;
    }


    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }


    public String getDescricao() {
        return descricao;
    }


    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }


    public String getDataInicio() {
        return dataInicio;
    }


    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }


    public String getDataConclusao() {
        return dataConclusao;
    }


    public void setDataConclusao(String dataConclusao) {
        this.dataConclusao = dataConclusao;
    }


    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }


    public String getPrioridade() {
        return prioridade;
    }


    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    
}
