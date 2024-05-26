package com.devmobile.viajei.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.devmobile.viajei.database.model.AlimentacaoModel;
import com.devmobile.viajei.database.model.AviaoTransporteModel;
import com.devmobile.viajei.database.model.CarroTransporteModel;
import com.devmobile.viajei.database.model.EntretenimentoModel;
import com.devmobile.viajei.database.model.HospedagemModel;
import com.devmobile.viajei.database.model.TransporteModel;
import com.devmobile.viajei.database.model.UsuarioModel;

public class DBOpenHelper extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "viajei.db";
    private static final int VERSAO_BANCO = 7;

    public DBOpenHelper(@Nullable Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UsuarioModel.CREATE_TABLE);
        db.execSQL(HospedagemModel.CREATE_TABLE);
        db.execSQL(AlimentacaoModel.CREATE_TABLE);
        db.execSQL(EntretenimentoModel.CREATE_TABLE);
        db.execSQL(TransporteModel.CREATE_TABLE);
        db.execSQL(AviaoTransporteModel.CREATE_TABLE);
        db.execSQL(CarroTransporteModel.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(UsuarioModel.DROP_TABLE);
        db.execSQL(HospedagemModel.DROP_TABLE);
        db.execSQL(AlimentacaoModel.DROP_TABLE);
        db.execSQL(EntretenimentoModel.DROP_TABLE);
        db.execSQL(TransporteModel.DROP_TABLE);
        db.execSQL(AviaoTransporteModel.DROP_TABLE);
        db.execSQL(CarroTransporteModel.DROP_TABLE);

        onCreate(db);
    }
}
