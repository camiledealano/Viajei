package com.devmobile.viajei;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.devmobile.viajei.database.dao.HomeDAO;
import com.devmobile.viajei.database.model.HomeModel;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private HomeDAO homeDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        EditText nomeDestino = findViewById(R.id.nome_destino);
        Button btnCriarDestino = findViewById(R.id.btn_pesquisar_destino);
        Button btnSimular = findViewById(R.id.btn_simular);
        LinearLayout layoutDestinoCriado = findViewById(R.id.layout_destino_criado);
        TextView textViewDestino = findViewById(R.id.textViewDestino);

        homeDAO = new HomeDAO(HomeActivity.this);

        SharedPreferences sharedPreferences   = PreferenceManager.getDefaultSharedPreferences(HomeActivity.this);
        String usuario = sharedPreferences.getString("nomeUsuario", "");

        TextView txtViewBoasVindas = findViewById(R.id.txt_boas_vindas);
        String boasVindasStr = "Olá " + usuario + "! Para onde você vai hoje?";
        txtViewBoasVindas.setText(boasVindasStr);

        long idUsuario = sharedPreferences.getLong("idUsuario", -1);

        buscaSimulacoes(idUsuario);


        btnCriarDestino.setOnClickListener(v -> {
            String destinoStr = nomeDestino.getText().toString();
            if (!destinoStr.isEmpty()) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("destino", destinoStr);
                editor.apply();

                textViewDestino.setText(destinoStr);
                layoutDestinoCriado.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(HomeActivity.this, "Por favor, insira um destino", Toast.LENGTH_SHORT).show();
            }

        });

        btnSimular.setOnClickListener(v -> {
            try {
                HomeModel homeModel = new HomeModel(
                        nomeDestino.getText().toString(),
                        idUsuario
                );

                homeDAO.insert(homeModel);
            } catch (Exception e) {
                Toast.makeText(HomeActivity.this, "Erro ao salvar destino: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            Intent intent = new Intent(HomeActivity.this, HospedagemActivity.class);
            startActivity(intent);
        });
    }

    private void buscaSimulacoes(long idUsuario) {
        List<HomeModel> simulacoes = homeDAO.findSimulacoesById(idUsuario);

        if (simulacoes.isEmpty()){
            return;
        }

        LinearLayout containerCards = findViewById(R.id.container_cards);

        for (HomeModel simulacao : simulacoes) {
            View cardView = LayoutInflater.from(this).inflate(R.layout.card_simulacoes, containerCards, false);

            TextView nomeDestino = cardView.findViewById(R.id.nome_destino_criado);
            nomeDestino.setText(simulacao.getNomeDestino());

            containerCards.addView(cardView);
        }
    }
}