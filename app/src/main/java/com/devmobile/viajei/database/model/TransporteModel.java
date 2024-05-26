package com.devmobile.viajei.database.model;

public class TransporteModel {

    public static final String TABELA_NOME = "tb_transporte";

    public static final String ID = "_id";
    public static final String ID_AVIAO_TRANSPORTE = "id_aviao_transporte";
    public static final String ID_CARRO_TRANSPORTE = "id_carro_transporte";
    public static final String ID_USUARIO = "id_usuario";
    public static final String CREATE_TABLE = "CREATE TABLE " + TABELA_NOME +
            "( "
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ID_USUARIO + " INTEGER, "
            + ID_AVIAO_TRANSPORTE + " INTEGER, "
            + ID_CARRO_TRANSPORTE + " INTEGER "
            + ");";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABELA_NOME;

    private int id;
    private long idAviaoTransporte;
    private long idCarroTransporte;
    private long idUsuario;

    public TransporteModel()
    {

    }

    public TransporteModel(long idAviaoTransporte, long idCarroTransporte, long idUsuario) {
        this.idAviaoTransporte = idAviaoTransporte;
        this.idCarroTransporte = idCarroTransporte;
        this.idUsuario = idUsuario;
    }

    public long getIdAviaoTransporte() {
        return idAviaoTransporte;
    }

    public void setIdAviaoTransporte(int idAviaoTransporte) {
        this.idAviaoTransporte = idAviaoTransporte;
    }

    public long getIdCarroTransporte() {
        return idCarroTransporte;
    }

    public void setIdCarroTransporte(int idCarroTransporte) {
        this.idCarroTransporte = idCarroTransporte;
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
}