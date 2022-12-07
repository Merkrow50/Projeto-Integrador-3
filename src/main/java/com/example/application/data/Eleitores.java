package com.example.application.data;

import lombok.Data;

@Data
public class Eleitores {

  private Long id;

  private String UF;

  private String municipio;

  private String municipioBiometrico;

  private String genero;

  private String estadoCivil;

  private String faixaIdade;

  private String escolaridade;

  private String totalEleitores;

  private String eleitoresBiometria;

  private String eleitoresDeficientes;

}
