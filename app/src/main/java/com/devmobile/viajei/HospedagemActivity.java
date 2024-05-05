package com.devmobile.viajei;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.devmobile.viajei.database.dao.HospedagemDAO;
import com.devmobile.viajei.database.model.HospedagemModel;
import com.devmobile.viajei.service.HospedagemService;

public class HospedagemActivity extends AppCompatActivity {

    private EditText custoMedio;
    private EditText qtdQuartos;
    private EditText qtdPessoas;
    private EditText qtdNoites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospedagem);

        custoMedio = findViewById(R.id.custo_medio);
        qtdQuartos = findViewById(R.id.qtd_quartos);
        qtdPessoas = findViewById(R.id.qtd_pessoas);
        qtdNoites = findViewById(R.id.qtd_noites);

        HospedagemService hospedagemService = new HospedagemService();

        Button btnCalcular = findViewById(R.id.btnHospedagemCalcular);
        Button btnAvancar = findViewById(R.id.btnHospedagemAvancar);

        btnCalcular.setOnClickListener(v -> {
            String custoMedioStr = custoMedio.getText().toString();
            String qtdQuartosStr = qtdQuartos.getText().toString();
            String qtdPessoasStr = qtdPessoas.getText().toString();
            String qtdNoitesStr = qtdNoites.getText().toString();

            if (custoMedioStr.isEmpty() || qtdQuartosStr.isEmpty() || qtdPessoasStr.isEmpty() || qtdNoitesStr.isEmpty()) {
                Toast.makeText(HospedagemActivity.this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }

            Double custoMedioValue = Double.valueOf(custoMedioStr);
            Integer qtdQuartosValue = Integer.valueOf(qtdQuartosStr);
            Integer qtdNoitesValue = Integer.valueOf(qtdNoitesStr);

            String totalHospedagem = hospedagemService.calcularTotalHospedagem(custoMedioValue, qtdNoitesValue, qtdQuartosValue);

            TextView totalHospedagemTextView = findViewById(R.id.total_hospedagem);
            String textoTotalHospedagem = getString(R.string.total_parcial) + totalHospedagem;
            totalHospedagemTextView.setText(textoTotalHospedagem);
        });

        btnAvancar.setOnClickListener(v -> {
            String custoMedioStr = custoMedio.getText().toString();
            String qtdQuartosStr = qtdQuartos.getText().toString();
            String qtdPessoasStr = qtdPessoas.getText().toString();
            String qtdNoitesStr = qtdNoites.getText().toString();

            if (custoMedioStr.isEmpty() || qtdQuartosStr.isEmpty() || qtdPessoasStr.isEmpty() || qtdNoitesStr.isEmpty()) {
                Toast.makeText(HospedagemActivity.this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }

            Double custoMedioValue = Double.valueOf(custoMedioStr);
            Integer qtdQuartosValue = Integer.valueOf(qtdQuartosStr);
            Integer qtdPessoasValue = Integer.valueOf(qtdPessoasStr);
            Integer qtdNoitesValue = Integer.valueOf(qtdNoitesStr);

            Double totalHospedagemValue = Double.valueOf(hospedagemService.calcularTotalHospedagem(custoMedioValue, qtdNoitesValue, qtdQuartosValue));

            HospedagemModel hospedagemModel = new HospedagemModel(
                    custoMedioValue,
                    qtdQuartosValue,
                    qtdPessoasValue,
                    qtdNoitesValue,
                    totalHospedagemValue
            );

            try {
                HospedagemDAO hospedagemDAO = new HospedagemDAO(HospedagemActivity.this);
                hospedagemDAO.insert(hospedagemModel);

                //TODO: redirecionar pra proxima tela
            } catch (Exception e) {
                Toast.makeText(HospedagemActivity.this, "Erro ao salvar hospedagem: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}