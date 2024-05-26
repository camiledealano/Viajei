package com.devmobile.viajei.database.model;

public class CarroTransporteModel {
    public static final String TABELA_NOME = "tb_carro_transporte";

    public static final String ID = "_id";
    public static final String VALOR_ALUGUEL = "valor_aluguel_carro";
    public static final String KILOMETRO_TOTAL = "kilometro_total";
    public static final String KM_LITRO = "km_litro";
    public static final String CUSTO_LITRO = "custo_litro";
    public static final String TOTAL_VEICULOS = "total_veiculos";
    public static final String TOTAL = "total";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABELA_NOME +
            "( "
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + VALOR_ALUGUEL + " NUMERIC, "
            + KILOMETRO_TOTAL+ " NUMERIC, "
            + KM_LITRO+ " NUMERIC, "
            + CUSTO_LITRO+ " NUMERIC, "
            + TOTAL_VEICULOS+ " INTEGER, "
            + TOTAL + " NUMERIC "
            + ");";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABELA_NOME;

    private int id;
    private double valorAluguelCarro;
    private double kilometroTotal;
    private double kmLitro;
    private double custoLitro;
    private int totalVeiculos;

    private double total;

    public CarroTransporteModel()
    {

    }

    public CarroTransporteModel(double valorAluguelCarro, double kilometroTotal, double kmLitro,
                                double custoLitro, int totalVeiculos, double total) {
        this.valorAluguelCarro = valorAluguelCarro;
        this.kilometroTotal = kilometroTotal;
        this.kmLitro = kmLitro;
        this.custoLitro = custoLitro;
        this.totalVeiculos = totalVeiculos;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValorAluguelCarro() {
        return valorAluguelCarro;
    }

    public void setValorAluguelCarro(double valorAluguelCarro) {
        this.valorAluguelCarro = valorAluguelCarro;
    }

    public double getKilometroTotal() {
        return kilometroTotal;
    }

    public void setKilometroTotal(double kilometroTotal) {
        this.kilometroTotal = kilometroTotal;
    }

    public double getKmLitro() {
        return kmLitro;
    }

    public void setKmLitro(double kmLitro) {
        this.kmLitro = kmLitro;
    }

    public double getCustoLitro() {
        return custoLitro;
    }

    public void setCustoLitro(double custoLitro) {
        this.custoLitro = custoLitro;
    }

    public int getTotalVeiculos() {
        return totalVeiculos;
    }

    public void setTotalVeiculos(int totalVeiculos) {
        this.totalVeiculos = totalVeiculos;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
