package com.devmobile.viajei.database.dao;

import android.content.Context;
import android.database.Cursor;

import com.devmobile.viajei.database.DBOpenHelper;
import com.devmobile.viajei.database.model.UsuarioModel;
import com.devmobile.viajei.database.utils.AbstractDAO;

public class LoginDAO extends AbstractDAO {

    private UsuarioModel usuarioModel;

    public LoginDAO(Context context) {
        db_helper = new DBOpenHelper(context);
    }

    public UsuarioModel getUsuarioPorEmail(String email) {

        String[] projection = {
                UsuarioModel.ID,
                UsuarioModel.EMAIL,
                UsuarioModel.SENHA
        };

        String selection = UsuarioModel.EMAIL + " = ?";
        String[] selectionArgs = { email };

        Cursor cursor = db.query(
                UsuarioModel.TABELA_NOME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(UsuarioModel.ID));
            String senha = cursor.getString(cursor.getColumnIndexOrThrow(UsuarioModel.SENHA));
            usuarioModel = new UsuarioModel(id, email, senha);
        }

        cursor.close();
        db.close();

        return usuarioModel;
    }
}
