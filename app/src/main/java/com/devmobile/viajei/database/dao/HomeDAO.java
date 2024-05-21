package com.devmobile.viajei.database.dao;

import android.content.ContentValues;
import android.content.Context;

import com.devmobile.viajei.database.DBOpenHelper;
import com.devmobile.viajei.database.model.HomeModel;
import com.devmobile.viajei.database.utils.AbstractDAO;
public class HomeDAO extends AbstractDAO {

    public HomeDAO (Context context) { db_helper = new DBOpenHelper(context);}

    public void insert(HomeModel homeModel) {
        try {
            open();

            ContentValues values = new ContentValues();
            values.put(HomeModel.NOME_DESTINO, homeModel.getNomeDestino());

            db.insert(HomeModel.TABELA_NOME, null, values);
        } finally {
            close();
        }
    }
}

