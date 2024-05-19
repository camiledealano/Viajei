package com.devmobile.viajei.database.dao;

import android.content.ContentValues;
import android.content.Context;

import com.devmobile.viajei.database.DBOpenHelper;
import com.devmobile.viajei.database.model.EntretenimentoModel;
import com.devmobile.viajei.database.utils.AbstractDAO;

public class EntretenimentoDAO extends AbstractDAO {

    public EntretenimentoDAO(Context context) {
        db_helper = new DBOpenHelper(context);
    }

    public void insert(EntretenimentoModel entretenimentoModel) {
        try {
            open();

            ContentValues values = new ContentValues();
            values.put(EntretenimentoModel.ID_USUARIO, entretenimentoModel.getIdUsuario());
            values.put(EntretenimentoModel.NOME_LAZER, entretenimentoModel.getNomeLazer());
            values.put(EntretenimentoModel.VALOR, entretenimentoModel.getValor().toString());
            values.put(EntretenimentoModel.TOTAL, entretenimentoModel.getTotal().toString());

            db.insert(EntretenimentoModel.TABELA_NOME, null, values);
        } finally {
            close();
        }
    }
}
