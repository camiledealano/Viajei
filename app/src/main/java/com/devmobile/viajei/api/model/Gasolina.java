package com.devmobile.viajei.api.model;

public class Gasolina {
    public Gasolina(int totalEstimadoKM, double mediaKMLitro, double custoMedioLitro, int totalVeiculos, int idConta) {
        this.totalEstimadoKM = totalEstimadoKM;
        this.mediaKMLitro = mediaKMLitro;
        this.custoMedioLitro = custoMedioLitro;
        this.totalVeiculos = totalVeiculos;
        this.idConta = idConta;
    }

    private int totalEstimadoKM;
    private double mediaKMLitro;
    private double custoMedioLitro;
    private int totalVeiculos;
    private int idConta;

    public int getTotalEstimadoKM() {
        return totalEstimadoKM;
    }

    public void setTotalEstimadoKM(int totalEstimadoKM) {
        this.totalEstimadoKM = totalEstimadoKM;
    }

    public double getMediaKMLitro() {
        return mediaKMLitro;
    }

    public void setMediaKMLitro(double mediaKMLitro) {
        this.mediaKMLitro = mediaKMLitro;
    }

    public double getCustoMedioLitro() {
        return custoMedioLitro;
    }

    public void setCustoMedioLitro(double custoMedioLitro) {
        this.custoMedioLitro = custoMedioLitro;
    }

    public int getTotalVeiculos() {
        return totalVeiculos;
    }

    public void setTotalVeiculos(int totalVeiculos) {
        this.totalVeiculos = totalVeiculos;
    }

    public int getIdConta() {
        return idConta;
    }

    public void setIdConta(int idConta) {
        this.idConta = idConta;
    }
}
