package com.devmobile.viajei;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.devmobile.viajei.adapter.EntretenimentoAdapter;
import com.devmobile.viajei.database.dao.EntretenimentoDAO;
import com.devmobile.viajei.database.model.EntretenimentoModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class EntretenimentoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_entretenimento);

        EditText nomeEntretenimento = findViewById(R.id.nome_entretenimento);
        EditText valorEntretenimento = findViewById(R.id.valor_entretenimento);

        Button btnAdicionar = findViewById(R.id.btn_adicionar_entretenimento);
        Button btnAvancar = findViewById(R.id.btn_entretenimento_avancar);
        ListView listaEntretenimentos = findViewById(R.id.lista_entretenimentos);

        EntretenimentoAdapter adapter = new EntretenimentoAdapter(EntretenimentoActivity.this);

        ArrayList<EntretenimentoModel> entretenimentos = new ArrayList<>();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(EntretenimentoActivity.this);
        String destino = sharedPreferences.getString("destino", "");
        int idUsuario = sharedPreferences.getInt("idUsuario", -1);

        TextView destinoTextView = findViewById(R.id.nome_destino);
        String textoDestino = getString(R.string.destino) + " " + destino;
        destinoTextView.setText(textoDestino);

        AtomicReference<BigDecimal> valorTotalAtomic = new AtomicReference<>(BigDecimal.ZERO);

        btnAdicionar.setOnClickListener(v -> {
            String nomeEntretenimentoStr = nomeEntretenimento.getText().toString();
            String valorEntretenimentoStr = valorEntretenimento.getText().toString();

            if (nomeEntretenimentoStr.isEmpty() || valorEntretenimentoStr.isEmpty()) {
                Toast.makeText(EntretenimentoActivity.this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }

            BigDecimal valorEntretenimentoValue = new BigDecimal(valorEntretenimentoStr);
            BigDecimal valorTotal = valorTotalAtomic.get().add(valorEntretenimentoValue);
            valorTotalAtomic.set(valorTotal);

            EntretenimentoModel entretenimentoModel = new EntretenimentoModel(
                    idUsuario,
                    nomeEntretenimentoStr,
                    valorEntretenimentoValue,
                    valorTotal
            );

            try {
                EntretenimentoDAO entretenimentoDAO = new EntretenimentoDAO(EntretenimentoActivity.this);
                entretenimentoDAO.insert(entretenimentoModel);
            } catch (Exception e) {
                Toast.makeText(EntretenimentoActivity.this, "Erro ao salvar entretenimento: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            entretenimentos.add(entretenimentoModel);
            adapter.setItens(entretenimentos);
            listaEntretenimentos.setAdapter(adapter);

            TextView totalEntretenimentoTextView = findViewById(R.id.total_entretenimento);
            String textoTotalEntretenimento = getString(R.string.total_parcial) + valorTotal;
            totalEntretenimentoTextView.setText(textoTotalEntretenimento);

            nomeEntretenimento.setText("");
            valorEntretenimento.setText("");
        });

        btnAvancar.setOnClickListener(v -> {
            //leva pra pagina de relatorios
        });
    }
}