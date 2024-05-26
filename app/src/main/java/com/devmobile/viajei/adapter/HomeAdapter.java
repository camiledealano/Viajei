package com.devmobile.viajei.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.devmobile.viajei.R;
import com.devmobile.viajei.database.model.EntretenimentoModel;
import com.devmobile.viajei.database.model.HomeModel;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends BaseAdapter {
    private Activity activity;
    private List<HomeModel> homeModelModels;

    public HomeAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setItens(List<HomeModel> modelArrayList) {
        this.homeModelModels = modelArrayList;
    }

    @Override
    public int getCount() {
        return this.homeModelModels != null ? this.homeModelModels.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return this.homeModelModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = activity.getLayoutInflater().inflate(R.layout.card_simulacoes, parent, false);
        }

        HomeModel homeModel = homeModelModels.get(position);

        TextView nomeDestino = convertView.findViewById(R.id.nome_destino_criado);
        nomeDestino.setText(homeModel.getNomeDestino());

        return convertView;
    }
}
