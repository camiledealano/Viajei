package com.devmobile.viajei.api.model;

public class Hospedagem {

    public Hospedagem(double custoMedioNoite, int totalNoite, int totalQuartos, int idConta) {
        this.custoMedioNoite = custoMedioNoite;
        this.totalNoite = totalNoite;
        this.totalQuartos = totalQuartos;
        this.idConta = idConta;
    }

    private double custoMedioNoite;
    private int totalNoite;
    private int totalQuartos;
    private int idConta;

    public double getCustoMedioNoite() {
        return custoMedioNoite;
    }

    public void setCustoMedioNoite(double custoMedioNoite) {
        this.custoMedioNoite = custoMedioNoite;
    }

    public int getTotalNoite() {
        return totalNoite;
    }

    public void setTotalNoite(int totalNoite) {
        this.totalNoite = totalNoite;
    }

    public int getTotalQuartos() {
        return totalQuartos;
    }

    public void setTotalQuartos(int totalQuartos) {
        this.totalQuartos = totalQuartos;
    }

    public int getIdConta() {
        return idConta;
    }

    public void setIdConta(int idConta) {
        this.idConta = idConta;
    }
}
