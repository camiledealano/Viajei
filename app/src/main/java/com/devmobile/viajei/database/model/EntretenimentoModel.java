package com.devmobile.viajei.database.model;

import java.math.BigDecimal;

public class EntretenimentoModel {

    public static final String TABELA_NOME = "tb_entretenimento";
    public static final String ID = "_id";
    public static final String ID_USUARIO = "id_usuario";
    public static final String NOME_LAZER = "nome_lazer";
    public static final String VALOR = "valor";
    public static final String TOTAL = "total";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABELA_NOME +
            "( "
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ID_USUARIO + " NUMERIC NOT NULL, "
            + NOME_LAZER+ " TEXT NOT NULL, "
            + VALOR+ " NUMERIC NOT NULL, "
            + TOTAL+ " NUMERIC NOT NULL " +
            ");";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABELA_NOME;

    private int id;
    private int idUsuario;
    private String nomeLazer;
    private BigDecimal valor;
    private BigDecimal total;

    public EntretenimentoModel(int idUsuario, String nomeLazer, BigDecimal valor, BigDecimal total) {
        this.idUsuario = idUsuario;
        this.nomeLazer = nomeLazer;
        this.valor = valor;
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

    public String getNomeLazer() {
        return nomeLazer;
    }

    public void setNomeLazer(String nomeLazer) {
        this.nomeLazer = nomeLazer;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
