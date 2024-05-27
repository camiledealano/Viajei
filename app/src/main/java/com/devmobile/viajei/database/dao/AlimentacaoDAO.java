package com.devmobile.viajei.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.devmobile.viajei.database.DBOpenHelper;
import com.devmobile.viajei.database.model.AlimentacaoModel;
import com.devmobile.viajei.database.model.AviaoTransporteModel;
import com.devmobile.viajei.database.utils.AbstractDAO;

public class AlimentacaoDAO extends AbstractDAO {

    public AlimentacaoDAO(Context context) {
        db_helper = new DBOpenHelper(context);
    }

    private final String[]
            colunas = {
            AlimentacaoModel.ID,
            AlimentacaoModel.ID_USUARIO,
            AlimentacaoModel.CUSTO_MEDIO,
            AlimentacaoModel.QTD_REFEICOES,
            AlimentacaoModel.TOTAL,
            AlimentacaoModel.ID_HOME
    };
    public void insertOrUpdate(AlimentacaoModel alimentacaoModel) {
        try {
            open();

            String idHomeString = Long.toString(alimentacaoModel.getIdHome());

            Cursor cursor = db.query(
                    AlimentacaoModel.TABELA_NOME,
                    colunas,
                    AlimentacaoModel.ID_HOME + " = ?",
                    new String[]{idHomeString},
                    null,
                    null,
                    null
            );

            ContentValues values = new ContentValues();
            values.put(AlimentacaoModel.ID_USUARIO, alimentacaoModel.getIdUsuario());
            values.put(AlimentacaoModel.CUSTO_MEDIO, alimentacaoModel.getCustoMedio());
            values.put(AlimentacaoModel.QTD_REFEICOES, alimentacaoModel.getQtdRefeicoes());
            values.put(AlimentacaoModel.TOTAL, alimentacaoModel.getTotal());
            values.put(AlimentacaoModel.ID_HOME, alimentacaoModel.getIdHome());

            if (cursor.moveToFirst()) {
                db.update(AlimentacaoModel.TABELA_NOME, values, AlimentacaoModel.ID_HOME + " = ?", new String[]{idHomeString});
            } else {
                db.insert(AlimentacaoModel.TABELA_NOME, null, values);
            }
            cursor.close();
        } finally {
            close();
        }
    }

    public AlimentacaoModel findByIdUsuario(final long id) {

        AlimentacaoModel model = null;
        String idString = Long.toString(id);
        try {
            open();
            Cursor cursor = db.query
                    (
                            AlimentacaoModel.TABELA_NOME,
                            colunas,
                            AlimentacaoModel.ID + " = ?",
                            new String[]{idString},
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

    public AlimentacaoModel findByIdHome(final long id) {

        AlimentacaoModel model = null;
        String idHome = Long.toString(id);
        try {
            open();
            Cursor cursor = db.query
                    (
                            AlimentacaoModel.TABELA_NOME,
                            colunas,
                            AlimentacaoModel.ID_HOME + " = ?",
                            new String[]{idHome},
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

    public final AlimentacaoModel cursorToStructure(Cursor cursor) {
        AlimentacaoModel model = new AlimentacaoModel();
        model.setId(cursor.getInt(0));
        model.setIdUsuario(cursor.getInt(1));
        model.setCustoMedio(cursor.getDouble(2));
        model.setQtdRefeicoes(cursor.getInt(3));
        model.setTotal(cursor.getDouble(4));
        model.setIdHome(cursor.getLong(5));
        return model;
    }
}
