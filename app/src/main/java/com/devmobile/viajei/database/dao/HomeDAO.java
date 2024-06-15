package com.devmobile.viajei.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.devmobile.viajei.database.DBOpenHelper;
import com.devmobile.viajei.database.model.HomeModel;
import com.devmobile.viajei.database.utils.AbstractDAO;

public class HomeDAO extends AbstractDAO {

    private final String[]
            colunas = {
            HomeModel.ID,
            HomeModel.NOME_DESTINO,
            HomeModel.ID_USUARIO
    };

    public HomeDAO (Context context) { db_helper = new DBOpenHelper(context);}

    public long insert(HomeModel homeModel) {
        try {
            open();

            ContentValues values = new ContentValues();
            values.put(HomeModel.NOME_DESTINO, homeModel.getNomeDestino());
            values.put(HomeModel.ID_USUARIO, homeModel.getIdUsuario());

            long id = db.insert(HomeModel.TABELA_NOME, null, values);
            return id;
        } finally {
            close();
        }
    }

    public HomeModel findByIdUsuario(final long idUsuario) {
        HomeModel model = null;
        try {
            open();
            Cursor cursor = db.query
                    (
                            HomeModel.TABELA_NOME,
                            colunas,
                            HomeModel.ID_USUARIO + " = ?",
                            new String[]{String.valueOf(idUsuario)},
                            null,
                            null,
                            "_id desc");
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

    public final HomeModel cursorToStructure(Cursor cursor) {
        HomeModel model = new HomeModel();
        model.setId(cursor.getInt(0));
        model.setNomeDestino(cursor.getString(1));
        model.setIdUsuario(cursor.getInt(2));
        return model;
    }
}

