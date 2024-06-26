package com.devmobile.viajei.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.devmobile.viajei.database.DBOpenHelper;
import com.devmobile.viajei.database.model.TransporteModel;
import com.devmobile.viajei.database.utils.AbstractDAO;

public class TransporteDAO extends AbstractDAO {
    private TransporteModel transporteModel;

    public TransporteDAO(Context context) {
        db_helper = new DBOpenHelper(context);
    }

    private final String[]
            colunas = {
            TransporteModel.ID,
            TransporteModel.ID_CARRO_TRANSPORTE,
            TransporteModel.ID_AVIAO_TRANSPORTE,
            TransporteModel.ID_USUARIO,
            TransporteModel.ID_HOME
    };

    public void insertOrUpdate(TransporteModel transporteModel) {
        try {
            open();

            String idHomeString = Long.toString(transporteModel.getIdHome());
            Cursor cursor = db.query(
                    TransporteModel.TABELA_NOME,
                    colunas,
                    TransporteModel.ID_HOME + " = ?",
                    new String[]{idHomeString},
                    null,
                    null,
                    null
            );

            ContentValues values = new ContentValues();
            values.put(TransporteModel.ID_AVIAO_TRANSPORTE, transporteModel.getIdAviaoTransporte());
            values.put(TransporteModel.ID_CARRO_TRANSPORTE, transporteModel.getIdCarroTransporte());
            values.put(TransporteModel.ID_USUARIO, transporteModel.getIdUsuario());
            values.put(TransporteModel.ID_HOME, transporteModel.getIdHome());

            if (cursor.moveToFirst()) {
                db.update(TransporteModel.TABELA_NOME, values, TransporteModel.ID_HOME + " = ?", new String[]{idHomeString});
            } else {
                db.insert(TransporteModel.TABELA_NOME, null, values);
            }
        } finally {
            close();
        }
    }

    public TransporteModel findByIdUsuario(final long idUsuario) {

        TransporteModel model = null;

        String idUsuarioString = Long.toString(idUsuario);

        try {
            open();
            Cursor cursor = db.query
                    (
                            TransporteModel.TABELA_NOME,
                            colunas,
                            TransporteModel.ID_USUARIO + " = ? ",
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

    public TransporteModel findByIdHome(final long idHome) {

        TransporteModel model = null;

        String idHomeString = Long.toString(idHome);

        try {
            open();
            Cursor cursor = db.query
                    (
                            TransporteModel.TABELA_NOME,
                            colunas,
                            TransporteModel.ID_HOME + " = ? ",
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

    public final TransporteModel cursorToStructure(Cursor cursor) {
        TransporteModel model = new TransporteModel();
        model.setId(cursor.getInt(0));
        model.setIdCarroTransporte(cursor.getInt(1));
        model.setIdAviaoTransporte(cursor.getInt(2));
        model.setIdUsuario(cursor.getInt(3));
        model.setIdHome(cursor.getLong(4));

        return model;
    }

}
