package com.devmobile.viajei.database.model;

public class HomeModel {

    public static final String TABELA_NOME = "tb_nome_destino";

    public static final String ID = "_id";

    public static final String NOME_DESTINO = "nome_destino";


    public static final String CREATE_TABLE = "CREATE TABLE " + TABELA_NOME +
            "( "
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NOME_DESTINO + " TEXT NOT NULL, " +
            ");";

    private int id;
    private String nome_destino;

    public HomeModel(String nome_destino) {
        this.nome_destino = nome_destino;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeDestino() {
        return nome_destino;
    }

    public void setNomeDestino(String nome_destino) {
        this.nome_destino = nome_destino;
    }
}





