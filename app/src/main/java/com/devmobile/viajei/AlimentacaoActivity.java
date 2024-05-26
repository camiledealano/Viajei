package com.devmobile.viajei;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.devmobile.viajei.database.dao.AlimentacaoDAO;
import com.devmobile.viajei.database.model.AlimentacaoModel;
import com.devmobile.viajei.service.AlimentacaoService;

public class AlimentacaoActivity extends AppCompatActivity {

    private EditText custoMedioAlimentacao;
    private EditText qtdRefeicoes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alimentacao);

        custoMedioAlimentacao = findViewById(R.id.custo_medio_alimentacao);
        qtdRefeicoes          = findViewById(R.id.qtd_refeicoes);

        Button btnCalcularAlimentacao = findViewById(R.id.btn_calcular_alimentacao);
        Button btnAvancar             = findViewById(R.id.btn_alimentacao_avancar);

        AlimentacaoService alimentacaoService = new AlimentacaoService();
        SharedPreferences sharedPreferences   = PreferenceManager.getDefaultSharedPreferences(AlimentacaoActivity.this);

        int qtdPessoas = sharedPreferences.getInt("qtdPessoas", 0);
        int diasViagem = sharedPreferences.getInt("qtdNoites", 0);
        int idUsuario  = sharedPreferences.getInt("idUsuario", -1);
        String destino = sharedPreferences.getString("destino", "");

        TextView destinoTextView = findViewById(R.id.nome_destino);
        String textoDestino = getString(R.string.destino) + " " + destino;
        destinoTextView.setText(textoDestino);

        btnCalcularAlimentacao.setOnClickListener(v -> {
            String custoMedioAlimentacaoStr = custoMedioAlimentacao.getText().toString();
            String qtdRefeicoesStr          = qtdRefeicoes.getText().toString();

            if (custoMedioAlimentacaoStr.isEmpty() || qtdRefeicoesStr.isEmpty()) {
                Toast.makeText(AlimentacaoActivity.this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }

            Double custoMedioAlimentacaoValue  = Double.valueOf(custoMedioAlimentacaoStr);
            Integer qtdRefeicoesValue          = Integer.valueOf(qtdRefeicoesStr);

            String totalAlimentacao = alimentacaoService.calcularAlimentacao(custoMedioAlimentacaoValue, qtdRefeicoesValue, qtdPessoas, diasViagem);

            TextView totalAlimentacaoTextView = findViewById(R.id.total_alimentacao);
            String textoTotalAlimentacao = getString(R.string.total_parcial) + totalAlimentacao;
            totalAlimentacaoTextView.setText(textoTotalAlimentacao);
        });

        btnAvancar.setOnClickListener(v-> {
            String custoMedioAlimentacaoStr = custoMedioAlimentacao.getText().toString();
            String qtdRefeicoesStr          = qtdRefeicoes.getText().toString();

            if (custoMedioAlimentacaoStr.isEmpty() || qtdRefeicoesStr.isEmpty()) {
                Toast.makeText(AlimentacaoActivity.this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }

            Double custoMedioAlimentacaoValue  = Double.valueOf(custoMedioAlimentacaoStr);
            Integer qtdRefeicoesValue          = Integer.valueOf(qtdRefeicoesStr);

            Double totalAlimentacao = Double.valueOf(alimentacaoService.calcularAlimentacao(custoMedioAlimentacaoValue, qtdRefeicoesValue, qtdPessoas, diasViagem));

            AlimentacaoModel alimentacaoModel = new AlimentacaoModel(
                    idUsuario,
                    custoMedioAlimentacaoValue,
                    qtdRefeicoesValue,
                    totalAlimentacao
            );

            try {
                AlimentacaoDAO alimentacaoDAO = new AlimentacaoDAO(AlimentacaoActivity.this);
                alimentacaoDAO.insert(alimentacaoModel);

                Intent intent = new Intent(AlimentacaoActivity.this, TransporteActivity.class);
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(AlimentacaoActivity.this, "Erro ao salvar alimentação: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}