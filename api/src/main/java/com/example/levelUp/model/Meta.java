package com.example.levelUp.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import com.example.levelUp.model.enums.Status;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "tab_metas")
public class Meta extends CadastroGeral {

    public Meta() {
    }

    public Meta(String titulo, String descricao, LocalDate dataInicio, LocalDate dataConclusao, Status status) {
        super(titulo, descricao, dataInicio, dataConclusao, status);
    }

    

    @Transient
    public boolean isConcluida() {
        return this.getStatus() == Status.CONCLUIDA;
    }

    @Transient
    public boolean isAtrasada() {
        return !isConcluida() &&
                LocalDate.now().isAfter(this.getDataConclusao());
    }

    @Transient
    public long getDiasRestantes() {
        if (isConcluida())
            return 0;
        return ChronoUnit.DAYS.between(LocalDate.now(), this.getDataConclusao());
    }

    @Transient
    public long getDiasDesdeInicio() {
        return ChronoUnit.DAYS.between(this.getDataInicio(), LocalDate.now());
    }

    @Transient
    public double getProgresso() {
        long totalDias = ChronoUnit.DAYS.between(
                this.getDataInicio(), this.getDataConclusao());
        long diasPassados = getDiasDesdeInicio();

        if (totalDias <= 0)
            return isConcluida() ? 100.0 : 0.0;

        double progresso = (diasPassados * 100.0) / totalDias;
        return Math.min(Math.max(progresso, 0.0), 100.0);
    }

    // Métodos de negócio
    public void concluir() {
        this.setStatus(Status.CONCLUIDA);
    }

    public void cancelar() {
        this.setStatus(Status.CANCELADA);
    }

    public boolean podeSerConcluida() {
        return this.getStatus() == Status.EM_ANDAMENTO ||
                this.getStatus() == Status.PENDENTE;
    }
}