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
import com.devmobile.viajei.database.dao.HospedagemDAO;
import com.devmobile.viajei.database.model.HospedagemModel;
import com.devmobile.viajei.extensios.Extensions;
import com.devmobile.viajei.service.HospedagemService;

public class HospedagemActivity extends AppCompatActivity {

    private EditText custoMedio;
    private EditText qtdQuartos;
    private EditText qtdPessoas;
    private EditText qtdNoites;

    SharedPreferences sharedPreferences;

    long idUsuario, idHome;
    TextView totalHospedagemTextView;

    String destino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospedagem);

        custoMedio = findViewById(R.id.custo_medio);
        qtdQuartos = findViewById(R.id.qtd_quartos);
        qtdPessoas = findViewById(R.id.qtd_pessoas);
        qtdNoites  = findViewById(R.id.qtd_noites);
        totalHospedagemTextView = findViewById(R.id.total_hospedagem);
        HospedagemService hospedagemService = new HospedagemService();

        Button btnCalcular = findViewById(R.id.btnHospedagemCalcular);
        Button btnAvancar  = findViewById(R.id.btnHospedagemAvancar);

        ObterSharedPreferences();
        RecuperarDados();

        TextView destinoTextView = findViewById(R.id.nome_destino);
        String textoDestino = getString(R.string.destino) + " " + destino;
        destinoTextView.setText(textoDestino);

        btnCalcular.setOnClickListener(v -> {
            String custoMedioStr = custoMedio.getText().toString();
            String qtdQuartosStr = qtdQuartos.getText().toString();
            String qtdPessoasStr = qtdPessoas.getText().toString();
            String qtdNoitesStr  = qtdNoites.getText().toString();

            if (custoMedioStr.isEmpty() || qtdQuartosStr.isEmpty() || qtdPessoasStr.isEmpty() || qtdNoitesStr.isEmpty()) {
                Toast.makeText(HospedagemActivity.this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }

            Double custoMedioValue  = Double.valueOf(custoMedioStr);
            Integer qtdQuartosValue = Integer.valueOf(qtdQuartosStr);
            Integer qtdNoitesValue  = Integer.valueOf(qtdNoitesStr);

            double totalHospedagem = hospedagemService.calcularTotalHospedagem(custoMedioValue, qtdNoitesValue, qtdQuartosValue);


            String textoTotalHospedagem = getString(R.string.total_parcial) + Extensions.formatToBRL(totalHospedagem);
            totalHospedagemTextView.setText(textoTotalHospedagem);
        });

        btnAvancar.setOnClickListener(v -> {
            String custoMedioStr = custoMedio.getText().toString();
            String qtdQuartosStr = qtdQuartos.getText().toString();
            String qtdPessoasStr = qtdPessoas.getText().toString();
            String qtdNoitesStr  = qtdNoites.getText().toString();

            if (custoMedioStr.isEmpty() || qtdQuartosStr.isEmpty() || qtdPessoasStr.isEmpty() || qtdNoitesStr.isEmpty()) {
                Toast.makeText(HospedagemActivity.this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }

            Double custoMedioValue  = Double.valueOf(custoMedioStr);
            Integer qtdQuartosValue = Integer.valueOf(qtdQuartosStr);
            Integer qtdPessoasValue = Integer.valueOf(qtdPessoasStr);
            Integer qtdNoitesValue  = Integer.valueOf(qtdNoitesStr);

            Double totalHospedagemValue = hospedagemService.calcularTotalHospedagem(custoMedioValue, qtdNoitesValue, qtdQuartosValue);

            HospedagemModel hospedagemModel = new HospedagemModel(
                    idUsuario,
                    custoMedioValue,
                    qtdQuartosValue,
                    qtdPessoasValue,
                    qtdNoitesValue,
                    totalHospedagemValue,
                    idHome
            );

            try {
                HospedagemDAO hospedagemDAO = new HospedagemDAO(HospedagemActivity.this);
                hospedagemDAO.insertOrUpdate(hospedagemModel);

                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putInt("qtdPessoas", Integer.parseInt(qtdPessoasStr));
                editor.putInt("qtdNoites", Integer.parseInt(qtdNoitesStr));
                editor.apply();

                Intent intent = new Intent(HospedagemActivity.this, AlimentacaoActivity.class);
                startActivity(intent);

            } catch (Exception e) {
                Toast.makeText(HospedagemActivity.this, "Erro ao salvar hospedagem: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void RecuperarDados() {
        if(idHome == -1){
            return;
        }

        HospedagemDAO hospedagemDAO = new HospedagemDAO(HospedagemActivity.this);
        HospedagemModel model = hospedagemDAO.findByIdHome(idHome);

        if(model == null){
            return;
        }

        custoMedio.setText(model.getCustoMedio().toString());
        qtdQuartos.setText(model.getQtdQuartos().toString());
        qtdPessoas.setText(model.getQtdPessoas().toString());
        qtdNoites.setText(model.getQtdNoites().toString());

        String textoTotalHospedagem = getString(R.string.total_parcial) + Extensions.formatToBRL(model.getTotal());
        totalHospedagemTextView.setText(textoTotalHospedagem);

    }

    private void ObterSharedPreferences() {
        sharedPreferences   = PreferenceManager.getDefaultSharedPreferences(HospedagemActivity.this);
        destino = sharedPreferences.getString("destino", "");
        idUsuario  = sharedPreferences.getLong("idUsuario", -1);
        idHome  = sharedPreferences.getLong("idHome", -1);
    }
}