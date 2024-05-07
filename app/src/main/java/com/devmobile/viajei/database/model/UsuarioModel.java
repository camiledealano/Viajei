package com.devmobile.viajei.database.model;

public class UsuarioModel {

    public static final String TABELA_NOME = "tb_usuario";
    public static final String ID = "_id";
    public static final String NOME = "nome";
    public static final String EMAIL = "email";
    public static final String TELEFONE = "telefone";
    public static final String SENHA = "senha";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABELA_NOME +
            "( "
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NOME + " TEXT NOT NULL, "
                + EMAIL+ " TEXT NOT NULL, "
                + TELEFONE+ " TEXT NOT NULL, "
                + SENHA+ " TEXT NOT NULL " +
            ");";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABELA_NOME;

    private int id;
    private String nome;
    private String email;
    private String telefone;
    private String senha;

    public UsuarioModel(String nome, String email, String telefone, String senha) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.senha = senha;
    }

    public UsuarioModel(int id, String email, String senha) {
        this.id = id;
        this.email = email;
        this.senha = senha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
