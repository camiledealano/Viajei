package com.devmobile.viajei.database.model;

public class AlimentacaoModel {

    public static final String TABELA_NOME = "tb_alimentacao";

    public static final String ID = "_id";
    public static final String ID_USUARIO = "id_usuario";
    public static final String CUSTO_MEDIO = "custo_medio";
    public static final String QTD_REFEICOES = "qtd_refeicoes";
    public static final String TOTAL = "total";
    public static final String ID_HOME = "id_home";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABELA_NOME +
            "( "
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ID_USUARIO + " NUMERIC NOT NULL, "
                + CUSTO_MEDIO+ " NUMERIC NOT NULL, "
                + QTD_REFEICOES+ " NUMERIC NOT NULL, "
                + TOTAL+ " NUMERIC NOT NULL, "
                + ID_HOME + " NUMERIC NOT NULL" +
            ");";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABELA_NOME;

    private int id;
    private long idUsuario;
    private Double custoMedio;
    private Integer qtdRefeicoes;
    private Double total;
    private long idHome;

    public AlimentacaoModel(){}
    public AlimentacaoModel(long idUsuario, Double custoMedio, Integer qtdRefeicoes, Double total, long idHome) {
        this.idUsuario = idUsuario;
        this.custoMedio = custoMedio;
        this.qtdRefeicoes = qtdRefeicoes;
        this.total = total;
        this.idHome = idHome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
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

    public long getIdHome() {
        return idHome;
    }

    public void setIdHome(long idHome) {
        this.idHome = idHome;
    }
}
