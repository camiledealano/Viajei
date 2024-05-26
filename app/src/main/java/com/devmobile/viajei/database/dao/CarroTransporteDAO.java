package com.devmobile.viajei.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.devmobile.viajei.database.DBOpenHelper;
import com.devmobile.viajei.database.model.CarroTransporteModel;
import com.devmobile.viajei.database.model.TransporteModel;
import com.devmobile.viajei.database.model.UsuarioModel;
import com.devmobile.viajei.database.utils.AbstractDAO;

public class CarroTransporteDAO extends AbstractDAO {
    private CarroTransporteModel carroTransporteModel;

    public CarroTransporteDAO(Context context) {
        db_helper = new DBOpenHelper(context);
    }

    private final String[] colunas = {
            CarroTransporteModel.ID,
            CarroTransporteModel.VALOR_ALUGUEL,
            CarroTransporteModel.KILOMETRO_TOTAL,
            CarroTransporteModel.KM_LITRO,
            CarroTransporteModel.CUSTO_LITRO,
            CarroTransporteModel.TOTAL_VEICULOS

    };

    public long insert(CarroTransporteModel carroTransporteModel) {
        long newRow = -1;
        try {
            open();

            ContentValues values = new ContentValues();
            values.put(CarroTransporteModel.VALOR_ALUGUEL, carroTransporteModel.getValorAluguelCarro());
            values.put(CarroTransporteModel.KILOMETRO_TOTAL, carroTransporteModel.getKilometroTotal());
            values.put(CarroTransporteModel.KM_LITRO, carroTransporteModel.getKmLitro());
            values.put(CarroTransporteModel.CUSTO_LITRO, carroTransporteModel.getCustoLitro());
            values.put(CarroTransporteModel.TOTAL_VEICULOS, carroTransporteModel.getTotalVeiculos());

            newRow = db.insert(CarroTransporteModel.TABELA_NOME, null, values);
        } finally {
            close();
        }

        return newRow;
    }

    public CarroTransporteModel Select(final long id) {

        CarroTransporteModel model = null;
        String idString = Long.toString(id);
        try {
            open();
            Cursor cursor = db.query
                    (
                            UsuarioModel.TABELA_NOME,
                            colunas,
                            CarroTransporteModel.ID + " = ? ",
                            new String[]{idString},
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

    public final CarroTransporteModel cursorToStructure(Cursor cursor) {
        CarroTransporteModel model = new CarroTransporteModel();

        model.setId(cursor.getInt(0));
        model.setValorAluguelCarro(cursor.getDouble(1));
        model.setKilometroTotal(cursor.getDouble(2));
        model.setKmLitro(cursor.getDouble(3));
        model.setCustoLitro(cursor.getDouble(4));
        model.setKilometroTotal(cursor.getDouble(5));

        return model;
    }
}
