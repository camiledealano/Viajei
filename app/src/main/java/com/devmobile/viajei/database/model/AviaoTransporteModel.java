package com.devmobile.viajei.database.model;

public class AviaoTransporteModel {
    public static final String TABELA_NOME = "tb_aviao_transporte";

    public static final String ID = "_id";
    public static final String VALOR_PASSAGEM = "valor_passagem";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABELA_NOME +
            "( "
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + VALOR_PASSAGEM + " NUMERIC "
            + ");";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABELA_NOME;

    private int id;
    private double valorPassagem;

    public AviaoTransporteModel(){
    }
    public AviaoTransporteModel(double valorPassagem){
        this.valorPassagem = valorPassagem;
    }

    public double getValorPassagem() {
        return valorPassagem;
    }

    public void setValorPassagem(double valorPassagem) {
        this.valorPassagem = valorPassagem;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
