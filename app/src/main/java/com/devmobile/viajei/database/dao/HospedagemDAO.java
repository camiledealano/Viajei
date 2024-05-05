package com.devmobile.viajei.database.dao;

import android.content.ContentValues;
import android.content.Context;

import com.devmobile.viajei.database.DBOpenHelper;
import com.devmobile.viajei.database.model.HospedagemModel;
import com.devmobile.viajei.database.utils.AbstractDAO;

public class HospedagemDAO extends AbstractDAO {

    public HospedagemDAO(Context context) {
        db_helper = new DBOpenHelper(context);
    }

    public void insert(HospedagemModel hospedagemModel) {
        try {
            open();

            ContentValues values = new ContentValues();
            values.put(HospedagemModel.CUSTO_MEDIO, hospedagemModel.getCustoMedio());
            values.put(HospedagemModel.QTD_QUARTOS, hospedagemModel.getQtdQuartos());
            values.put(HospedagemModel.QTD_PESSOAS, hospedagemModel.getQtdPessoas());
            values.put(HospedagemModel.QTD_NOITES, hospedagemModel.getQtdNoites());
            values.put(HospedagemModel.TOTAL, hospedagemModel.getTotal());
            //TODO: nao tem q inserir o id do usuario logado tbm?

            db.insert(HospedagemModel.TABELA_NOME, null, values);
        } finally {
            close();
        }
    }
}
