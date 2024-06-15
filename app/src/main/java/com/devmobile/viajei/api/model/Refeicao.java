package com.devmobile.viajei.api.model;

public class Refeicao {

    public Refeicao(double custoRefeicao, int refeicoesDia, int idConta) {
        this.custoRefeicao = custoRefeicao;
        this.refeicoesDia = refeicoesDia;
        this.idConta = idConta;
    }

    private double custoRefeicao;
    private int refeicoesDia;
    private int idConta;

    public double getCustoRefeicao() {
        return custoRefeicao;
    }

    public void setCustoRefeicao(double custoRefeicao) {
        this.custoRefeicao = custoRefeicao;
    }

    public int getRefeicoesDia() {
        return refeicoesDia;
    }

    public void setRefeicoesDia(int refeicoesDia) {
        this.refeicoesDia = refeicoesDia;
    }

    public int getIdConta() {
        return idConta;
    }

    public void setIdConta(int idConta) {
        this.idConta = idConta;
    }
}
