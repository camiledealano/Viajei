package com.devmobile.viajei.service;

public class AlimentacaoService {

    /**
     * Calcula o custo total da alimentação, baseado no custo médio, quantidade de refeições, dias de viagem e quantiade de pessoas.
     *
     * @param custoMedioAlimentacao O custo médio da alimentação por noite.
     * @param qtdRefeicoes A quantidade de refeições diárias.
     * @param qtdViajantes A quantidade de viajantes.
     * @param diasViagem Dias totais de viagem.
     * @return O custo total da alimentação.
     */
    public double calcularAlimentacao(Double custoMedioAlimentacao, Integer qtdRefeicoes, Integer qtdViajantes, Integer diasViagem) {
        Double total = ((qtdRefeicoes * qtdViajantes) * custoMedioAlimentacao) * diasViagem;
        return total;
    }
}
