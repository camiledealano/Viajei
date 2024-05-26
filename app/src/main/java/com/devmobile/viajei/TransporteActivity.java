package com.devmobile.viajei;

import static com.devmobile.viajei.extensios.Extensions.ParseDouble;
import static com.devmobile.viajei.extensios.Extensions.ParseInt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.devmobile.viajei.database.dao.AviaoTransporteDAO;
import com.devmobile.viajei.database.dao.CarroTransporteDAO;
import com.devmobile.viajei.database.dao.TransporteDAO;
import com.devmobile.viajei.database.model.AviaoTransporteModel;
import com.devmobile.viajei.database.model.CarroTransporteModel;
import com.devmobile.viajei.database.model.TransporteModel;

public class TransporteActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,TextWatcher, CompoundButton.OnCheckedChangeListener {

    Button btnAdicionar, btnTransporteAvancar;
    EditText kmTotal, totalVeiculos, custoPorLitro, kmPorLitro, valorAluguelCarro, valorPassagemAerea;
    TextView destinoTextView, totalCarroTransporteTextView, qtdPessoasTextView;

    int idUsuario,selectedTransport,qtdPessoas;
    long idNewAviaoTransporte = 0, idNewCarroTransporte = 0;
    String destino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_transporte);

        GetSharedPreferencesData();
        SetDestinoView();
        setQtdPessoasView();
        GetTransporteArrayData();

        CheckBox checkBoxAluguelCarro = findViewById(R.id.checkBox_aluguel_carro);
        valorPassagemAerea = findViewById(R.id.valor_passagem_avisao);
        valorAluguelCarro = findViewById(R.id.valor_aluguel_carro);
        kmPorLitro = findViewById(R.id.km_litro);
        custoPorLitro = findViewById(R.id.custo_litro);
        totalVeiculos = findViewById(R.id.total_veiculos);
        kmTotal = findViewById(R.id.km_total);
        btnAdicionar = findViewById(R.id.btn_transporte_adicionar);
        btnTransporteAvancar = findViewById(R.id.btn_transporte_avancar);

        valorPassagemAerea.addTextChangedListener(this);
        valorAluguelCarro.addTextChangedListener(this);
        kmPorLitro.addTextChangedListener(this);
        custoPorLitro.addTextChangedListener(this);
        totalVeiculos.addTextChangedListener(this);
        kmTotal.addTextChangedListener(this);

        btnAdicionar.setOnClickListener( x -> adicionar());

        btnTransporteAvancar.setOnClickListener(v -> {
            Intent intent = new Intent(TransporteActivity.this, EntretenimentoActivity.class);
            startActivity(intent);
        });

        checkBoxAluguelCarro.setOnCheckedChangeListener(this);
    }

    private void SetDestinoView() {
        destinoTextView = findViewById(R.id.nome_destino);
        String textoDestino = getString(R.string.destino) + " " + destino;
        destinoTextView.setText(textoDestino);
    }

    private void setQtdPessoasView() {
        qtdPessoasTextView = findViewById(R.id.qtd_pessoas_transporte);
        String textoQtdPessoas = "Para " + qtdPessoas + " pessoas.";
        qtdPessoasTextView.setText(textoQtdPessoas);
    }

    private void GetTransporteArrayData() {
        Spinner spinner = (Spinner) findViewById(R.id.transporte_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.transporte_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    private void GetSharedPreferencesData() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(TransporteActivity.this);

        destino = sharedPreferences.getString("destino", "");
        idUsuario = sharedPreferences.getInt("idUsuario", -1);
        qtdPessoas = sharedPreferences.getInt("qtdPessoas", 1);
    }

    private void adicionar() {
        switch (selectedTransport){
            case 0:
                InsertAviaoTransporte();
                break;
            case 1:
                InsertCarroTransporte();
                break;
        }

        InsertTransporte();

    }

    private void InsertAviaoTransporte() {
        AviaoTransporteModel aviaoTransporteModel = new AviaoTransporteModel(
                ParseDouble(valorPassagemAerea.getText().toString())
        );

        try {
            AviaoTransporteDAO aviaoTransporteDAO = new AviaoTransporteDAO(TransporteActivity.this);
            idNewAviaoTransporte = aviaoTransporteDAO.insert(aviaoTransporteModel);

            Toast.makeText(TransporteActivity.this, "Transporte inserido com sucesso!", Toast.LENGTH_SHORT).show();
            //TODO: redirecionar pra home
        } catch (Exception e) {
            Toast.makeText(TransporteActivity.this, "Erro ao inserir Dados do transporte: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void InsertTransporte() {
        TransporteModel transporteModel = new TransporteModel(
                idNewAviaoTransporte,
                idNewCarroTransporte,
                idUsuario
        );

        try {
            TransporteDAO transporteDAO = new TransporteDAO(TransporteActivity.this);
            transporteDAO.insert(transporteModel);

            Toast.makeText(TransporteActivity.this, "Transporte inserido com sucesso!", Toast.LENGTH_SHORT).show();
            //TODO: redirecionar pra home
        } catch (Exception e) {
            Toast.makeText(TransporteActivity.this, "Erro ao inserir Dados do transporte: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void InsertCarroTransporte() {
        CarroTransporteModel carroTransporteModel = new CarroTransporteModel(
                ParseDouble(valorAluguelCarro.getText().toString()),
                ParseDouble(kmTotal.getText().toString()),
                ParseDouble(kmPorLitro.getText().toString()),
                ParseDouble(custoPorLitro.getText().toString()),
                Integer.parseInt(totalVeiculos.getText().toString())
        );

        try {
            CarroTransporteDAO carroTransporteDAO = new CarroTransporteDAO(TransporteActivity.this);
            idNewCarroTransporte = carroTransporteDAO.insert(carroTransporteModel);

            Toast.makeText(TransporteActivity.this, "Transporte inserido com sucesso!", Toast.LENGTH_SHORT).show();
            //TODO: redirecionar pra home
        } catch (Exception e) {
            Toast.makeText(TransporteActivity.this, "Erro ao inserir Dados do transporte: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedTransport = position;
        CardView cardCarro = findViewById(R.id.card_carro);
        CardView cardAviao = findViewById(R.id.card_aviao);

        switch (position) {
            case 0:
                cardCarro.setVisibility(View.GONE);
                cardAviao.setVisibility(View.VISIBLE);
                break;
            case 1:
                cardAviao.setVisibility(View.GONE);
                cardCarro.setVisibility(View.VISIBLE);
                break;
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        updateTotal(s);
    }
    private void updateTotal(CharSequence s) {
        TextView totalAviaoTransporteTextView = findViewById(R.id.total_aviao_transporte);
        try {
            double valorPassagem = Double.parseDouble(s.toString());
            double total = valorPassagem * qtdPessoas; // Calculate the total here
            totalAviaoTransporteTextView.setText(String.format("Total: %.2f", total));
        } catch (NumberFormatException e) {
            totalAviaoTransporteTextView.setText("Total: 0.00");
        }
    }
    @Override
    public void afterTextChanged(Editable s) {
        calculateTotal();
    }
    private void calculateTotal() {
        totalCarroTransporteTextView = findViewById(R.id.total_carro_transporte);

        try {
            double valorAluguel = ParseDouble(valorAluguelCarro.getText().toString());
            double kmTotalTrajeto = ParseDouble(kmTotal.getText().toString());
            double kmLitro = ParseDouble(kmPorLitro.getText().toString());
            double custoLitro = ParseDouble(custoPorLitro.getText().toString());
            int totalDeVeiculos = ParseInt(totalVeiculos.getText().toString());
            double total = (((kmTotalTrajeto / kmLitro ) * custoLitro) / totalDeVeiculos) + valorAluguel;

            if(Double.isNaN(total) || Double.isInfinite(total)){
                totalCarroTransporteTextView.setText(String.format("Insira todos os dados."));
            } else {
                totalCarroTransporteTextView.setText(String.format("Total: %.2f", total));
            }

        } catch (NumberFormatException e) {
            totalCarroTransporteTextView.setText("Total: 0.00");
        }
    }
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        LinearLayout layoutAluguelCarro = findViewById(R.id.layout_aluguel_carro);

        if (isChecked) {
            layoutAluguelCarro.setVisibility(View.VISIBLE);
        } else {
            layoutAluguelCarro.setVisibility(View.GONE);
        }
    }

}