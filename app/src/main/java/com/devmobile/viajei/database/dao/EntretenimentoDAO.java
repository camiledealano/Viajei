package com.devmobile.viajei.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.devmobile.viajei.database.DBOpenHelper;
import com.devmobile.viajei.database.model.EntretenimentoModel;
import com.devmobile.viajei.database.utils.AbstractDAO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class EntretenimentoDAO extends AbstractDAO {

    public EntretenimentoDAO(Context context) {
        db_helper = new DBOpenHelper(context);
    }

    private final String[]
            colunas = {
            EntretenimentoModel.ID,
            EntretenimentoModel.ID_USUARIO,
            EntretenimentoModel.NOME_LAZER,
            EntretenimentoModel.VALOR,
            EntretenimentoModel.TOTAL,
            EntretenimentoModel.ID_HOME
    };
    public void insertOrUpdate(EntretenimentoModel entretenimentoModel) {
        try {
            open();

            String idHomeString = Long.toString(entretenimentoModel.getIdHome());
            Cursor cursor = db.query(
                    EntretenimentoModel.TABELA_NOME,
                    colunas,
                    EntretenimentoModel.ID_HOME + " = ?",
                    new String[]{idHomeString},
                    null,
                    null,
                    null
            );

            ContentValues values = new ContentValues();
            values.put(EntretenimentoModel.ID_USUARIO, entretenimentoModel.getIdUsuario());
            values.put(EntretenimentoModel.NOME_LAZER, entretenimentoModel.getNomeLazer());
            values.put(EntretenimentoModel.VALOR, entretenimentoModel.getValor().toString());
            values.put(EntretenimentoModel.TOTAL, entretenimentoModel.getTotal().toString());
            values.put(EntretenimentoModel.ID_HOME, entretenimentoModel.getIdHome());

            if (cursor.moveToFirst()) {
                db.update(EntretenimentoModel.TABELA_NOME, values, EntretenimentoModel.ID_HOME + " = ?", new String[]{idHomeString});
            } else {
                db.insert(EntretenimentoModel.TABELA_NOME, null, values);
            }
            cursor.close();
        } finally {
            close();
        }
    }

    public List<EntretenimentoModel> findByIdUsuario(final long idUsuario) {

        List<EntretenimentoModel> modelList = new ArrayList<>();

        String idUsuarioString = Long.toString(idUsuario);

        try {
            open();
            Cursor cursor = db.query
                    (
                            EntretenimentoModel.TABELA_NOME,
                            colunas,
                            EntretenimentoModel.ID_USUARIO + " = ? ",
                            new String[]{idUsuarioString},
                            null,
                            null,
                            "_id desc");
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                EntretenimentoModel model = cursorToStructure(cursor);
                modelList.add(model);
                cursor.moveToNext();
            }
        }
        finally {
            close();
        }

        return modelList;
    }

    public List<EntretenimentoModel> findByIdHome(final long idHome) {

        List<EntretenimentoModel> modelList = new ArrayList<>();

        String idHomeString = Long.toString(idHome);

        try {
            open();
            Cursor cursor = db.query
                    (
                            EntretenimentoModel.TABELA_NOME,
                            colunas,
                            EntretenimentoModel.ID_HOME + " = ? ",
                            new String[]{idHomeString},
                            null,
                            null,
                            null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                EntretenimentoModel model = cursorToStructure(cursor);
                modelList.add(model);
                cursor.moveToNext();
            }
        }
        finally {
            close();
        }

        return modelList;
    }

    public final EntretenimentoModel cursorToStructure(Cursor cursor) {
        EntretenimentoModel model = new EntretenimentoModel();
        model.setId(cursor.getInt(0));
        model.setIdUsuario(cursor.getInt(1));
        model.setNomeLazer(cursor.getString(2));
        model.setValor(new BigDecimal(cursor.getString(3)));
        model.setTotal(new BigDecimal(cursor.getString(4)));
        model.setIdHome(cursor.getLong(4));
        return model;
    }
}
