package com.devmobile.viajei;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.devmobile.viajei.database.dao.HomeDAO;
import com.devmobile.viajei.database.model.HomeModel;
import com.devmobile.viajei.database.model.HospedagemModel;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        EditText nomeDestino = findViewById(R.id.nome_destino);
        Button btnCriarDestino = findViewById(R.id.btn_pesquisar_destino);
        Button btnSimular = findViewById(R.id.btn_simular);
        Button btnVisualizar = findViewById(R.id.btn_visualizar_relatorio);
        LinearLayout layoutDestinoCriado = findViewById(R.id.layout_destino_criado);
        TextView textViewDestino = findViewById(R.id.textViewDestino);

        SharedPreferences sharedPreferences   = PreferenceManager.getDefaultSharedPreferences(HomeActivity.this);
        String usuario = sharedPreferences.getString("nomeUsuario", "");
        long idUsuario = sharedPreferences.getLong("idUsuario", -1);

        TextView txtViewBoasVindas = findViewById(R.id.txt_boas_vindas);
        String boasVindasStr = "Olá " + usuario + "! Para onde você vai hoje?";
        txtViewBoasVindas.setText(boasVindasStr);

        buscaSimulacao(idUsuario);

        btnCriarDestino.setOnClickListener(v -> {
            String destino_destino = nomeDestino.getText().toString();
            if (!destino_destino.isEmpty()) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("destino", destino_destino);
                editor.apply();

                textViewDestino.setText(destino_destino);
                layoutDestinoCriado.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(HomeActivity.this, "Por favor, insira um destino", Toast.LENGTH_SHORT).show();
            }

        });

        btnSimular.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, HospedagemActivity.class);
            startActivity(intent);
        });

        btnVisualizar.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, RelatorioActivity.class);
            startActivity(intent);
        });
    }

    private void buscaSimulacao(long idUsuario) {
        HomeDAO homeDAO = new HomeDAO(HomeActivity.this);
        HomeModel model = homeDAO.findByIdUsuario(idUsuario);

        if (model != null) {
            LinearLayout layoutSimulacao = findViewById(R.id.container_simulacao);
            TextView nomeDestinoCriadoTextView = findViewById(R.id.nome_destino_criado);
            nomeDestinoCriadoTextView.setText(model.getNomeDestino());

            layoutSimulacao.setVisibility(View.VISIBLE);
        }
    }
}