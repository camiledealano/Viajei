package com.devmobile.viajei.database.dao;

import android.content.ContentValues;
import android.content.Context;

import com.devmobile.viajei.database.DBOpenHelper;
import com.devmobile.viajei.database.model.AlimentacaoModel;
import com.devmobile.viajei.database.utils.AbstractDAO;

public class AlimentacaoDAO extends AbstractDAO {

    public AlimentacaoDAO(Context context) {
        db_helper = new DBOpenHelper(context);
    }

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
}
