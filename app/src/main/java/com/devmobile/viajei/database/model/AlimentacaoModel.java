package com.devmobile.viajei.database.model;

public class AlimentacaoModel {

    public static final String TABELA_NOME = "tb_alimentacao";
    public static final String ID = "_id";
    public static final String ID_USUARIO = "id_usuario";
    public static final String CUSTO_MEDIO = "custo_medio";
    public static final String QTD_REFEICOES = "qtd_refeicoes";
    public static final String TOTAL = "total";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABELA_NOME +
            "( "
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ID_USUARIO + " NUMERIC NOT NULL, "
                + CUSTO_MEDIO+ " NUMERIC NOT NULL, "
                + QTD_REFEICOES+ " NUMERIC NOT NULL, "
                + TOTAL+ " NUMERIC NOT NULL " +
            ");";


    private int id;
    private int idUsuario;
    private Double custoMedio;
    private Integer qtdRefeicoes;
    private Double total;

    public AlimentacaoModel(int idUsuario, Double custoMedio, Integer qtdRefeicoes, Double total) {
        this.idUsuario = idUsuario;
        this.custoMedio = custoMedio;
        this.qtdRefeicoes = qtdRefeicoes;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Double getCustoMedio() {
        return custoMedio;
    }

    public void setCustoMedio(Double custoMedio) {
        this.custoMedio = custoMedio;
    }

    public Integer getQtdRefeicoes() {
        return qtdRefeicoes;
    }

    public void setQtdRefeicoes(Integer qtdRefeicoes) {
        this.qtdRefeicoes = qtdRefeicoes;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}