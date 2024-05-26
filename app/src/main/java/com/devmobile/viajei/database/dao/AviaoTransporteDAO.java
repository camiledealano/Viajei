package com.devmobile.viajei.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.devmobile.viajei.database.DBOpenHelper;
import com.devmobile.viajei.database.model.AlimentacaoModel;
import com.devmobile.viajei.database.model.AviaoTransporteModel;
import com.devmobile.viajei.database.model.TransporteModel;
import com.devmobile.viajei.database.model.UsuarioModel;
import com.devmobile.viajei.database.utils.AbstractDAO;

public class AviaoTransporteDAO extends AbstractDAO {
    public AviaoTransporteDAO(Context context) {
        db_helper = new DBOpenHelper(context);
    }

    private final String[]
            colunas = {
            AviaoTransporteModel.ID,
            AviaoTransporteModel.VALOR_PASSAGEM
    };

    public long insert(AviaoTransporteModel aviaoTransporteModel) {
        long newRow = -1;
        try {
            open();

            ContentValues values = new ContentValues();
            values.put(AviaoTransporteModel.VALOR_PASSAGEM, aviaoTransporteModel.getValorPassagem());

            newRow = db.insert(AviaoTransporteModel.TABELA_NOME, null, values);
        } finally {
            close();
        }

        return newRow;
    }

    public AviaoTransporteModel findById(final long id) {

        AviaoTransporteModel model = null;
        String idString = Long.toString(id);
        try {
            open();
            Cursor cursor = db.query
                    (
                            AviaoTransporteModel.TABELA_NOME,
                            colunas,
                            AviaoTransporteModel.ID + " = ?",
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

    public final AviaoTransporteModel cursorToStructure(Cursor cursor) {
        AviaoTransporteModel model = new AviaoTransporteModel();
        model.setId(cursor.getInt(0));
        model.setValorPassagem(cursor.getDouble(1));
        return model;
    }
}
