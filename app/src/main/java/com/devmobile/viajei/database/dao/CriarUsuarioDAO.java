package com.devmobile.viajei.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import com.devmobile.viajei.database.DBOpenHelper;
import com.devmobile.viajei.database.model.UsuarioModel;
import com.devmobile.viajei.database.utils.AbstractDAO;

public class CriarUsuarioDAO extends AbstractDAO {

    private UsuarioModel usuarioModel;

    public CriarUsuarioDAO(Context context) {
        db_helper = new DBOpenHelper(context);
    }

    private final String[]
            colunas = {
            UsuarioModel.ID,
            UsuarioModel.NOME,
            UsuarioModel.EMAIL,
            UsuarioModel.SENHA
    };

    public long insert(UsuarioModel usuarioModel) {
        long idCriado;
        try {
            open();

            ContentValues values = new ContentValues();
            values.put(UsuarioModel.NOME, usuarioModel.getNome());
            values.put(UsuarioModel.EMAIL, usuarioModel.getEmail());
            values.put(UsuarioModel.TELEFONE, usuarioModel.getTelefone());
            values.put(UsuarioModel.SENHA, usuarioModel.getSenha());

            idCriado = db.insert(UsuarioModel.TABELA_NOME, null, values);
        } finally {
            close();
        }

        return idCriado;
    }

    public UsuarioModel Select(final String email, final String senha) {

        UsuarioModel model = null;

        try {
            open();
            Cursor cursor = db.query
                    (
                            UsuarioModel.TABELA_NOME,
                            colunas,
                            UsuarioModel.EMAIL + " = ? and "+UsuarioModel.SENHA+" = ?",
                            new String[]{email, senha},
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

    public final UsuarioModel cursorToStructure(Cursor cursor) {
        UsuarioModel model = new UsuarioModel();
        model.setId(cursor.getInt(0));
        model.setEmail(cursor.getString(2));
        model.setSenha(cursor.getString(3));
        model.setNome(cursor.getString(1));
        return model;
    }
}
