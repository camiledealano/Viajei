package com.devmobile.viajei.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.devmobile.viajei.database.model.HospedagemModel;
import com.devmobile.viajei.database.model.UsuarioModel;

public class DBOpenHelper extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "viajei.db";
    private static final int VERSAO_BANCO = 1;

    public DBOpenHelper(@Nullable Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UsuarioModel.CREATE_TABLE);
        db.execSQL(HospedagemModel.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
