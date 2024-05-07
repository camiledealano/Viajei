package com.devmobile.viajei.database.utils;

import android.database.sqlite.SQLiteDatabase;

import com.devmobile.viajei.database.DBOpenHelper;

public abstract class AbstractDAO {

    protected SQLiteDatabase db;
    protected DBOpenHelper db_helper;

    protected void open() {
        db = db_helper.getWritableDatabase();
    }

    protected void close() {
        db_helper.close();
    }


}
