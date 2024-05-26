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
            AlimentacaoModel.TOTAL
    };
    public void insert(AlimentacaoModel alimentacaoModel) {
        try {
            open();

            ContentValues values = new ContentValues();
            values.put(AlimentacaoModel.ID_USUARIO, alimentacaoModel.getIdUsuario());
            values.put(AlimentacaoModel.CUSTO_MEDIO, alimentacaoModel.getCustoMedio());
            values.put(AlimentacaoModel.QTD_REFEICOES, alimentacaoModel.getQtdRefeicoes());
            values.put(AlimentacaoModel.TOTAL, alimentacaoModel.getTotal());

            db.insert(AlimentacaoModel.TABELA_NOME, null, values);
        } finally {
            close();
        }
    }

    public AlimentacaoModel FindByIdUsuario(final long id) {

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

    public final AlimentacaoModel cursorToStructure(Cursor cursor) {
        AlimentacaoModel model = new AlimentacaoModel();
        model.setId(cursor.getInt(0));
        model.setIdUsuario(cursor.getInt(1));
        model.setCustoMedio(cursor.getDouble(2));
        model.setQtdRefeicoes(cursor.getInt(3));
        model.setTotal(cursor.getDouble(4));

        return model;
    }
}
