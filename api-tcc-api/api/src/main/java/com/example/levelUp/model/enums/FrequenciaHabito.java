package com.example.levelUp.model.enums;

public enum FrequenciaHabito {
  DIARIA("Diaria"),
  SEMANAL("Semanal"),
  MENSAL("Mensal");

  private String texto;

  FrequenciaHabito(String texto) {
    this.texto = texto;
  }

  public String getTexto() {
    return texto;
  }

}
