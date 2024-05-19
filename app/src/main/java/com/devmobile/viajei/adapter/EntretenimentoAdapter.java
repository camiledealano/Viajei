package com.devmobile.viajei.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.devmobile.viajei.R;
import com.devmobile.viajei.database.model.EntretenimentoModel;

import java.util.ArrayList;

public class EntretenimentoAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<EntretenimentoModel> entretenimentos;

    public EntretenimentoAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setItens(ArrayList<EntretenimentoModel> listaEntretenimentos) {
        this.entretenimentos = listaEntretenimentos;
    }

    @Override
    public int getCount() {
        return this.entretenimentos != null ? this.entretenimentos.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return this.entretenimentos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = activity.getLayoutInflater().inflate(R.layout.item_listview_entretenimento, parent, false);
        }

        EntretenimentoModel entretenimento = entretenimentos.get(position);

        TextView nomeLazer = convertView.findViewById(R.id.item_nome_lazer);
        nomeLazer.setText(entretenimento.getNomeLazer());

        TextView valorLazer = convertView.findViewById(R.id.item_valor);
        valorLazer.setText(String.valueOf(entretenimento.getValor()));

        return convertView;
    }
}
