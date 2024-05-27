package com.devmobile.viajei;

import android.content.Intent;
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
import com.devmobile.viajei.extensios.Extensions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class EntretenimentoActivity extends AppCompatActivity {

    long idUsuario, idHome;
    String destino;
    EntretenimentoAdapter adapter;
    ArrayList<EntretenimentoModel> entretenimentos;
    ListView listaEntretenimentos;
    TextView totalEntretenimentoTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_entretenimento);

        EditText nomeEntretenimento = findViewById(R.id.nome_entretenimento);
        EditText valorEntretenimento = findViewById(R.id.valor_entretenimento);
        totalEntretenimentoTextView = findViewById(R.id.total_entretenimento);
        Button btnAdicionar = findViewById(R.id.btn_adicionar_entretenimento);
        Button btnAvancar = findViewById(R.id.btn_entretenimento_avancar);
        listaEntretenimentos = findViewById(R.id.lista_entretenimentos);

        adapter = new EntretenimentoAdapter(EntretenimentoActivity.this);
        entretenimentos = new ArrayList<>();

        obterSharedPreferences();
        obterDados();

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
                    valorTotal,
                    idHome
            );

            try {
                EntretenimentoDAO entretenimentoDAO = new EntretenimentoDAO(EntretenimentoActivity.this);
                entretenimentoDAO.insertOrUpdate(entretenimentoModel);
            } catch (Exception e) {
                Toast.makeText(EntretenimentoActivity.this, "Erro ao salvar entretenimento: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            entretenimentos.add(entretenimentoModel);
            adapter.setItens(entretenimentos);
            listaEntretenimentos.setAdapter(adapter);

            String textoTotalEntretenimento = getString(R.string.total_parcial) + Extensions.formatToBRL(valorTotal.doubleValue());
            totalEntretenimentoTextView.setText(textoTotalEntretenimento);

            nomeEntretenimento.setText("");
            valorEntretenimento.setText("");
        });

        btnAvancar.setOnClickListener(v -> {
            Intent intent = new Intent(EntretenimentoActivity.this, RelatorioActivity.class);
            startActivity(intent);
        });
    }

    private void obterDados() {
        if(idHome == -1 ){
            return;
        }

        EntretenimentoDAO entretenimentoDAO = new EntretenimentoDAO(EntretenimentoActivity.this);
        List<EntretenimentoModel> entretenimentoModelList = entretenimentoDAO.findByIdHome(idHome);

        BigDecimal total = BigDecimal.ZERO;;
        for(EntretenimentoModel model : entretenimentoModelList){
            entretenimentos.add(model);
            total = total.add(model.getTotal());
        }

        adapter.setItens(entretenimentos);
        listaEntretenimentos.setAdapter(adapter);

        String textoTotalEntretenimento = getString(R.string.total_parcial) + Extensions.formatToBRL(total.doubleValue());
        totalEntretenimentoTextView.setText(textoTotalEntretenimento);
    }

    private void obterSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(EntretenimentoActivity.this);
        destino = sharedPreferences.getString("destino", "");
        idUsuario = sharedPreferences.getLong("idUsuario", -1);
        idHome = sharedPreferences.getLong("idHome", -1);
    }
}