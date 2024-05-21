package com.devmobile.viajei;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        EditText nomeDestino = findViewById(R.id.nome_destino);
        Button btnCriarDestino = findViewById(R.id.btn_criar_destino);
        Button btnSimular = findViewById(R.id.btn_simular);
        LinearLayout layoutDestinoCriado = findViewById(R.id.layout_destino_criado);
        TextView textViewDestino = findViewById(R.id.textViewDestino);

        SharedPreferences sharedPreferences   = PreferenceManager.getDefaultSharedPreferences(HomeActivity.this);
        String destino = sharedPreferences.getString("destino", "");
        int idUsuario  = sharedPreferences.getInt("idUsuario", -1);

        btnCriarDestino.setOnClickListener(v -> {
            String destino_destino = nomeDestino.getText().toString();
            if (!destino_destino.isEmpty()) {
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
    }

}