package com.devmobile.viajei.service;

public class HospedagemService {

    /**
     * Calcula o custo total da hospedagem com base no custo médio por noite, quantidade de noites e número de quartos.
     *
     * @param custoMedio O custo médio da hospedagem por noite.
     * @param qtdNoites A quantidade de noites da estadia.
     * @param qtdQuartos A quantidade de quartos reservados.
     * @return O custo total da hospedagem.
     */
    public double calcularTotalHospedagem(Double custoMedio, Integer qtdNoites, Integer qtdQuartos) {
        double total = custoMedio * qtdNoites * qtdQuartos;
        return total;
    }
}
