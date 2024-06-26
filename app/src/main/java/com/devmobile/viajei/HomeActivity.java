package com.devmobile.viajei;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.hotspot2.pps.HomeSp;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.devmobile.viajei.api.Api;
import com.devmobile.viajei.api.model.Hospedagem;
import com.devmobile.viajei.api.model.Resposta;
import com.devmobile.viajei.api.model.Viagem;
import com.devmobile.viajei.database.dao.HomeDAO;
import com.devmobile.viajei.database.model.HomeModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    String usuario, destino_destino;

    Long idUsuario;
    SharedPreferences sharedPreferences;

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

        sharedPreferences   = PreferenceManager.getDefaultSharedPreferences(HomeActivity.this);
        usuario = sharedPreferences.getString("nomeUsuario", "");
        idUsuario = sharedPreferences.getLong("idUsuario", -1);

        TextView txtViewBoasVindas = findViewById(R.id.txt_boas_vindas);
        String boasVindasStr = "Olá " + usuario + "! Para onde você vai hoje?";
        txtViewBoasVindas.setText(boasVindasStr);

        buscaSimulacao(idUsuario);

        btnCriarDestino.setOnClickListener(v -> {
            destino_destino = nomeDestino.getText().toString();
            if (!destino_destino.isEmpty()) {
                destino_destino = destino_destino.substring(0, 1).toUpperCase() + destino_destino.substring(1);

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
            InserirHome();
            Intent intent = new Intent(HomeActivity.this, HospedagemActivity.class);
            startActivity(intent);
        });

        btnVisualizar.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, HospedagemActivity.class);
            startActivity(intent);
        });
    }

    private void InserirHome() {
        HomeDAO homeDAO = new HomeDAO(HomeActivity.this);
        HomeModel model = new HomeModel(destino_destino, idUsuario);
        long idHome = homeDAO.insert(model);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("idHome", idHome);
        editor.apply();
    }

    private void buscaSimulacao(long idUsuario) {

        Api.getViagem(131469, new Callback<ArrayList<Viagem>>() {
            @Override
            public void onResponse(Call<ArrayList<Viagem>> call, Response<ArrayList<Viagem>> response) {
                if (response != null && response.isSuccessful()) {
                    ArrayList<Viagem> viagens = response.body();
                    if (!viagens.isEmpty()) {
                        viagens.forEach(v -> {
                            LinearLayout layoutSimulacao = findViewById(R.id.container_simulacao);
                            TextView nomeDestinoCriadoTextView = findViewById(R.id.nome_destino_criado);
                            nomeDestinoCriadoTextView.setText(v.getLocal());
                            layoutSimulacao.setVisibility(View.VISIBLE);
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Viagem>> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Erro ao buscar viagens salvas.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}