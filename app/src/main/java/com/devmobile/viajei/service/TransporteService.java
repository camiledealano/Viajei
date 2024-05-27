package com.devmobile.viajei.service;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class TransporteService {
    public static double calcularTotal(double kmTotalTrajeto, double kmLitro, double custoLitro, int totalDeVeiculos, double valorAluguel) {
        double total = (((kmTotalTrajeto / kmLitro ) * custoLitro) / totalDeVeiculos) + valorAluguel;
        return total;
    }
}
