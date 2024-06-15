package com.devmobile.viajei.service;

public class TransporteService {
    public static double calcularTotal(double kmTotalTrajeto, double kmLitro, double custoLitro, int totalDeVeiculos, double valorAluguel) {
        double total = (((kmTotalTrajeto / kmLitro ) * custoLitro) / totalDeVeiculos) + valorAluguel;
        return total;
    }
}
