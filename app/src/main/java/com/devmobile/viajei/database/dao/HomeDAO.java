package com.devmobile.viajei.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.devmobile.viajei.database.DBOpenHelper;
import com.devmobile.viajei.database.model.HomeModel;
import com.devmobile.viajei.database.utils.AbstractDAO;

import java.util.ArrayList;
import java.util.List;

public class HomeDAO extends AbstractDAO {

    private final String[]
            colunas = {
            HomeModel.ID,
            HomeModel.NOME_DESTINO,
            HomeModel.ID_USUARIO
    };

    public HomeDAO (Context context) { db_helper = new DBOpenHelper(context);}

    public void insert(HomeModel homeModel) {
        try {
            open();

            ContentValues values = new ContentValues();
            values.put(HomeModel.NOME_DESTINO, homeModel.getNomeDestino());
            values.put(HomeModel.ID_USUARIO, homeModel.getIdUsuario());

            db.insert(HomeModel.TABELA_NOME, null, values);
        } finally {
            close();
        }
    }

    public List<HomeModel> findSimulacoesById(final long idUsuario) {
        HomeModel model;
        List<HomeModel> destinosSimulados = new ArrayList<>();

        try {
            open();
            //pau na query
            Cursor cursor = db.query
                    (
                            HomeModel.TABELA_NOME,
                            colunas,
                            HomeModel.ID_USUARIO + " = ?",
                            new String[]{String.valueOf(idUsuario)},
                            null,
                            null,
                            null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                model = cursorToStructure(cursor);
                destinosSimulados.add(model);
                break;
            }
        }
        finally {
            close();
        }

        return destinosSimulados;
    }

    public final HomeModel cursorToStructure(Cursor cursor) {
        HomeModel model = new HomeModel();
        model.setId(cursor.getInt(0));
        model.setIdUsuario(cursor.getInt(2));
        model.setNomeDestino(cursor.getString(3));
        return model;
    }
}

