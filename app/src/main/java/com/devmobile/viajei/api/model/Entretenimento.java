package com.devmobile.viajei.api.model;

public class Entretenimento {

    public Entretenimento(double valor, String entretenimento, int idConta) {
        this.valor = valor;
        this.entretenimento = entretenimento;
        this.idConta = idConta;
    }

    private double valor;
    private String entretenimento;
    private int idConta;

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getEntretenimento() {
        return entretenimento;
    }

    public void setEntretenimento(String entretenimento) {
        this.entretenimento = entretenimento;
    }

    public int getIdConta() {
        return idConta;
    }

    public void setIdConta(int idConta) {
        this.idConta = idConta;
    }
}
