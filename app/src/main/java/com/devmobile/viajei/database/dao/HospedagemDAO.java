package com.devmobile.viajei.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import androidx.annotation.NonNull;

import com.devmobile.viajei.database.DBOpenHelper;
import com.devmobile.viajei.database.model.HospedagemModel;
import com.devmobile.viajei.database.model.TransporteModel;
import com.devmobile.viajei.database.model.UsuarioModel;
import com.devmobile.viajei.database.utils.AbstractDAO;

public class HospedagemDAO extends AbstractDAO {

    public HospedagemDAO(Context context) {
        db_helper = new DBOpenHelper(context);
    }

    private final String[]
            colunas = {
            HospedagemModel.ID,
            HospedagemModel.CUSTO_MEDIO,
            HospedagemModel.QTD_QUARTOS,
            HospedagemModel.QTD_PESSOAS,
            HospedagemModel.QTD_NOITES,
            HospedagemModel.TOTAL,
            HospedagemModel.ID_USUARIO,
            HospedagemModel.ID_HOME
    };

    public void insertOrUpdate(@NonNull HospedagemModel hospedagemModel) {
        try {
            String idHome = Long.toString(hospedagemModel.getIdHome());
            open();

            String idHomeString = Long.toString(hospedagemModel.getIdHome());
            Cursor cursor = db.query(
                    HospedagemModel.TABELA_NOME,
                    colunas,
                    HospedagemModel.ID_HOME + " = ?",
                    new String[]{idHome},
                    null,
                    null,
                    null
            );

            ContentValues values = new ContentValues();
            values.put(HospedagemModel.ID, hospedagemModel.getIdUsuario());
            values.put(HospedagemModel.CUSTO_MEDIO, hospedagemModel.getCustoMedio());
            values.put(HospedagemModel.QTD_QUARTOS, hospedagemModel.getQtdQuartos());
            values.put(HospedagemModel.QTD_PESSOAS, hospedagemModel.getQtdPessoas());
            values.put(HospedagemModel.QTD_NOITES, hospedagemModel.getQtdNoites());
            values.put(HospedagemModel.TOTAL, hospedagemModel.getTotal());
            values.put(HospedagemModel.ID_USUARIO, hospedagemModel.getIdUsuario());
            values.put(HospedagemModel.ID_HOME, hospedagemModel.getIdHome());

            if (cursor.moveToFirst()) {
                db.update(HospedagemModel.TABELA_NOME, values, HospedagemModel.ID_HOME + " = ?", new String[]{idHomeString});
            } else {
                db.insert(HospedagemModel.TABELA_NOME, null, values);
            }
            cursor.close();
        } finally {
            close();
        }
    }

    public HospedagemModel findByIdUsuario(final long idUsuario) {

        HospedagemModel model = null;

        String idUsuarioString = Long.toString(idUsuario);

        try {
            open();
            Cursor cursor = db.query
                    (
                            HospedagemModel.TABELA_NOME,
                            colunas,
                            HospedagemModel.ID_USUARIO + " = ? ",
                            new String[]{idUsuarioString},
                            null,
                            null,
                            null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                model = cursorToStructure(cursor);
                break;
            }
        }
        finally {
            close();
        }

        return model;
    }

    public HospedagemModel findByIdHome(final long idHome) {

        HospedagemModel model = null;

        String idHomeString = Long.toString(idHome);

        try {
            open();
            Cursor cursor = db.query
                    (
                            HospedagemModel.TABELA_NOME,
                            colunas,
                            HospedagemModel.ID_USUARIO + " = ? ",
                            new String[]{idHomeString},
                            null,
                            null,
                            null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                model = cursorToStructure(cursor);
                break;
            }
        }
        finally {
            close();
        }

        return model;
    }

    public final HospedagemModel cursorToStructure(Cursor cursor) {
        HospedagemModel model = new HospedagemModel();
        model.setId(cursor.getInt(0));
        model.setCustoMedio(cursor.getDouble(1));
        model.setQtdQuartos(cursor.getInt(2));
        model.setQtdPessoas(cursor.getInt(3));
        model.setQtdNoites(cursor.getInt(4));
        model.setTotal(cursor.getDouble(5));
        model.setIdUsuario(cursor.getInt(6));
        model.setIdHome(cursor.getLong(7));

        return model;
    }
}
