package com.devmobile.viajei.database.model;


public class HospedagemModel {

    public static final String TABELA_NOME = "tb_hospedagem";
    public static final String ID = "_id";
    public static final String CUSTO_MEDIO = "custo_medio";
    public static final String QTD_QUARTOS = "qtd_quartos";
    public static final String QTD_PESSOAS = "qtd_pessoas";
    public static final String QTD_NOITES = "qtd_noites";
    public static final String TOTAL = "total";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABELA_NOME +
            "( "
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CUSTO_MEDIO + " NUMERIC NOT NULL, "
                + QTD_QUARTOS+ " NUMERIC NOT NULL, "
                + QTD_PESSOAS+ " NUMERIC NOT NULL, "
                + QTD_NOITES+ " NUMERIC NOT NULL, "
                + TOTAL+ " NUMERIC NOT NULL " +
            ");";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABELA_NOME;

    private int id;
    private Double custoMedio;
    private Integer qtdQuartos;
    private Integer qtdPessoas;
    private Integer qtdNoites;
    private Double total;
    //TODO: id do usuario?

    public HospedagemModel(Double custoMedio, Integer qtdQuartos, Integer qtdPessoas, Integer qtdNoites, Double total) {
        this.custoMedio = custoMedio;
        this.qtdQuartos = qtdQuartos;
        this.qtdPessoas = qtdPessoas;
        this.qtdNoites = qtdNoites;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getCustoMedio() {
        return custoMedio;
    }

    public void setCustoMedio(Double custoMedio) {
        this.custoMedio = custoMedio;
    }

    public Integer getQtdQuartos() {
        return qtdQuartos;
    }

    public void setQtdQuartos(Integer qtdQuartos) {
        this.qtdQuartos = qtdQuartos;
    }

    public Integer getQtdPessoas() {
        return qtdPessoas;
    }

    public void setQtdPessoas(Integer qtdPessoas) {
        this.qtdPessoas = qtdPessoas;
    }

    public Integer getQtdNoites() {
        return qtdNoites;
    }

    public void setQtdNoites(Integer qtdNoites) {
        this.qtdNoites = qtdNoites;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
