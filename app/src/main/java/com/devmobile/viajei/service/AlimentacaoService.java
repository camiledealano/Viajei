package com.devmobile.viajei.service;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

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
    public String calcularAlimentacao(Double custoMedioAlimentacao, Integer qtdRefeicoes, Integer qtdViajantes, Integer diasViagem) {
        Double total = ((qtdRefeicoes * qtdViajantes) * custoMedioAlimentacao) * diasViagem;
        DecimalFormat df = new DecimalFormat("###,##0.00", new DecimalFormatSymbols(Locale.getDefault()));
        return df.format(total);
    }
}
