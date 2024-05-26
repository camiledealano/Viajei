package com.devmobile.viajei.service;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class TransporteService {
    public static String calcularTotal(double kmTotalTrajeto, double kmLitro, double custoLitro, int totalDeVeiculos, double valorAluguel) {
        double total = (((kmTotalTrajeto / kmLitro ) * custoLitro) / totalDeVeiculos) + valorAluguel;
        DecimalFormat df = new DecimalFormat("###,##0.00", new DecimalFormatSymbols(Locale.getDefault()));
        return df.format(total);
    }
}
