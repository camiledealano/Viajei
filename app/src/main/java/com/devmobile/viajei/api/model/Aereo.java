package com.devmobile.viajei.api.model;

public class Aereo {
    public Aereo(double custoPessoa, double custoAluguelVeiculo, int idConta) {
        this.custoPessoa = custoPessoa;
        this.custoAluguelVeiculo = custoAluguelVeiculo;
        this.idConta = idConta;
    }

    private double custoPessoa;
    private double custoAluguelVeiculo;
    private int idConta;

    public double getCustoPessoa() {
        return custoPessoa;
    }

    public void setCustoPessoa(double custoPessoa) {
        this.custoPessoa = custoPessoa;
    }

    public double getCustoAluguelVeiculo() {
        return custoAluguelVeiculo;
    }

    public void setCustoAluguelVeiculo(double custoAluguelVeiculo) {
        this.custoAluguelVeiculo = custoAluguelVeiculo;
    }

    public int getIdConta() {
        return idConta;
    }

    public void setIdConta(int idConta) {
        this.idConta = idConta;
    }
}
