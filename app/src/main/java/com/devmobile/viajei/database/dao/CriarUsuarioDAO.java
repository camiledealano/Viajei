package com.devmobile.viajei.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.widget.Toast;

import com.devmobile.viajei.database.DBOpenHelper;
import com.devmobile.viajei.database.model.UsuarioModel;
import com.devmobile.viajei.database.utils.AbstractDAO;

public class CriarUsuarioDAO extends AbstractDAO {

    public CriarUsuarioDAO(Context context) {
        db_helper = new DBOpenHelper(context);
    }

    public void insert(UsuarioModel usuarioModel) {
        try {
            open();

            ContentValues values = new ContentValues();
            values.put(UsuarioModel.NOME, usuarioModel.getNome());
            values.put(UsuarioModel.EMAIL, usuarioModel.getEmail());
            values.put(UsuarioModel.TELEFONE, usuarioModel.getTelefone());
            values.put(UsuarioModel.SENHA, usuarioModel.getSenha());

            db.insert(UsuarioModel.TABELA_NOME, null, values);
        } finally {
            close();
        }
    }
}
