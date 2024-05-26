package com.devmobile.viajei.database.model;

public class HomeModel {

    public static final String TABELA_NOME = "tb_nome_destino";

    public static final String ID = "_id";
    public static final String ID_USUARIO = "id_usuario";
    public static final String NOME_DESTINO = "nome_destino";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABELA_NOME +
            "( "
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ID_USUARIO + " NUMERIC NOT NULL, "
            + NOME_DESTINO + " TEXT NOT NULL" +
            ");";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABELA_NOME;

    private int id;
    private String nomeDestino;
    private long idUsuario;

    public HomeModel(String nomeDestino, long idUsuario) {
        this.nomeDestino = nomeDestino;
        this.idUsuario = idUsuario;
    }

    public HomeModel(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeDestino() {
        return nomeDestino;
    }

    public void setNomeDestino(String nomeDestino) {
        this.nomeDestino = nomeDestino;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }
}





