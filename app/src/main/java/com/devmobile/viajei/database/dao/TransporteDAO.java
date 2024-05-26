package com.devmobile.viajei.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.devmobile.viajei.CriarUsuarioActivity;
import com.devmobile.viajei.TransporteActivity;
import com.devmobile.viajei.database.DBOpenHelper;
import com.devmobile.viajei.database.model.TransporteModel;
import com.devmobile.viajei.database.model.UsuarioModel;
import com.devmobile.viajei.database.utils.AbstractDAO;

import java.util.List;

public class TransporteDAO extends AbstractDAO {
    private TransporteModel transporteModel;

    public TransporteDAO(Context context) {
        db_helper = new DBOpenHelper(context);
    }

    private final String[]
            colunas = {
            TransporteModel.ID,
            TransporteModel.ID_CARRO_TRANSPORTE,
            TransporteModel.ID_AVIAO_TRANSPORTE,
            TransporteModel.ID_USUARIO
    };

    public void insert(TransporteModel transporteModel) {
        int newRow = -1;
        try {
            open();

            ContentValues values = new ContentValues();
            values.put(TransporteModel.ID_AVIAO_TRANSPORTE, transporteModel.getIdAviaoTransporte());
            values.put(TransporteModel.ID_CARRO_TRANSPORTE, transporteModel.getIdCarroTransporte());
            values.put(TransporteModel.ID_USUARIO, transporteModel.getIdUsuario());

            db.insert(TransporteModel.TABELA_NOME, null, values);
        } finally {
            close();
        }
    }

    public TransporteModel findByIdUsuario(final int idUsuario) {

        TransporteModel model = null;

        String idUsuarioString = Integer.toString(idUsuario);

        try {
            open();
            Cursor cursor = db.query
                    (
                            UsuarioModel.TABELA_NOME,
                            colunas,
                            TransporteModel.ID_USUARIO + " = ? ",
                            new String[]{idUsuarioString},
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

    public final TransporteModel cursorToStructure(Cursor cursor) {
        TransporteModel model = new TransporteModel();
        model.setId(cursor.getInt(0));
        model.setIdAviaoTransporte(cursor.getInt(1));
        model.setIdCarroTransporte(cursor.getInt(2));
        model.setIdUsuario(cursor.getInt(3));
        return model;
    }

}
