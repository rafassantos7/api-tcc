package com.example.aula.model;

import jakarta.persistence.*;
import java.time.LocalDate;

    @Entity
    @Table(name = "tab_habitos")
    public class Habitos extends Cadastro {

        private Boolean feito = false;

        public Habitos(Boolean feito) {
            this.feito = feito;
        }

        public Boolean getFeito() {
            return feito;
        }

        public void setFeito(Boolean feito) {
            this.feito = feito;
        }

        // Getters, setters e método confirmarRealizacao()
        public void confirmarRealizacao() {
            this.feito = true;
            this.dataConclusao = LocalDate.now();
        }
    }
