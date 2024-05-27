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
import com.devmobile.viajei.extensios.Extensions;
import com.devmobile.viajei.service.AlimentacaoService;

public class AlimentacaoActivity extends AppCompatActivity {

    private EditText custoMedioAlimentacao, qtdRefeicoes;
    private String destino;
    private long idUsuario, idHome;
    private int diasViagem, qtdPessoas;
    private TextView destinoTextView, totalAlimentacaoTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alimentacao);

        custoMedioAlimentacao = findViewById(R.id.custo_medio_alimentacao);
        qtdRefeicoes          = findViewById(R.id.qtd_refeicoes);
        destinoTextView = findViewById(R.id.nome_destino);
        totalAlimentacaoTextView = findViewById(R.id.total_alimentacao);
        Button btnCalcularAlimentacao = findViewById(R.id.btn_calcular_alimentacao);
        Button btnAvancar             = findViewById(R.id.btn_alimentacao_avancar);

        AlimentacaoService alimentacaoService = new AlimentacaoService();

        obterSharedPreferences();
        recuperarDados();

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

            double totalAlimentacao = alimentacaoService.calcularAlimentacao(custoMedioAlimentacaoValue, qtdRefeicoesValue, qtdPessoas, diasViagem);

            totalAlimentacaoTextView = findViewById(R.id.total_alimentacao);
            String textoTotalAlimentacao = getString(R.string.total_parcial) + Extensions.formatToBRL(totalAlimentacao);
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

            Double totalAlimentacao = alimentacaoService.calcularAlimentacao(custoMedioAlimentacaoValue, qtdRefeicoesValue, qtdPessoas, diasViagem);

            AlimentacaoModel alimentacaoModel = new AlimentacaoModel(
                    idUsuario,
                    custoMedioAlimentacaoValue,
                    qtdRefeicoesValue,
                    totalAlimentacao,
                    idHome
            );

            try {
                AlimentacaoDAO alimentacaoDAO = new AlimentacaoDAO(AlimentacaoActivity.this);
                alimentacaoDAO.insertOrUpdate(alimentacaoModel);

                Intent intent = new Intent(AlimentacaoActivity.this, TransporteActivity.class);
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(AlimentacaoActivity.this, "Erro ao salvar alimentação: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void recuperarDados() {

        if(idHome == -1){
            return;
        }

        AlimentacaoDAO alimentacaoDAO = new AlimentacaoDAO(AlimentacaoActivity.this);
        AlimentacaoModel model = alimentacaoDAO.findByIdHome(idHome);

        if(model == null){
            return;
        }

        qtdRefeicoes.setText(model.getQtdRefeicoes().toString());
        custoMedioAlimentacao.setText(model.getCustoMedio().toString());


        String textoTotalAlimentacao = getString(R.string.total_parcial) + Extensions.formatToBRL(model.getTotal());
        totalAlimentacaoTextView.setText(textoTotalAlimentacao);
    }

    private void obterSharedPreferences() {
        SharedPreferences sharedPreferences   = PreferenceManager.getDefaultSharedPreferences(AlimentacaoActivity.this);

        qtdPessoas = sharedPreferences.getInt("qtdPessoas", 0);
        diasViagem = sharedPreferences.getInt("qtdNoites", 0);
        idUsuario  = sharedPreferences.getLong("idUsuario", -1);
        destino = sharedPreferences.getString("destino", "");
        idHome  = sharedPreferences.getLong("idHome", -1);
    }
}