package com.devmobile.viajei;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

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
import com.devmobile.viajei.extensios.Extensions;
import com.devmobile.viajei.service.TransporteService;

public class TransporteActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,TextWatcher, CompoundButton.OnCheckedChangeListener {

    private Button btnAdicionar, btnAvancar;
    private EditText kmTotal, totalVeiculos, custoPorLitro, kmPorLitro, valorAluguelCarro, valorPassagemAerea;
    private TextView destinoTextView, totalCarroTransporteTextView, totalAviaoTransporteTextView;
    private boolean checkboxAluguel;
    private CheckBox checkBox;
    private int selectedTransport,qtdPessoas;
    private long idUsuario, idNewAviaoTransporte = 0, idNewCarroTransporte = 0, idHome;
    private String destino;
    private boolean adicionouTransporte = false;
    CardView cardCarro, cardAviao;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_transporte);

        checkBox = findViewById(R.id.checkBox_aluguel_carro);
        valorPassagemAerea = findViewById(R.id.valor_passagem_avisao);
        valorAluguelCarro = findViewById(R.id.valor_aluguel_carro);
        kmPorLitro = findViewById(R.id.km_litro);
        custoPorLitro = findViewById(R.id.custo_litro);
        totalVeiculos = findViewById(R.id.total_veiculos);
        kmTotal = findViewById(R.id.km_total);
        btnAdicionar = findViewById(R.id.btn_transporte_adicionar);
        btnAvancar = findViewById(R.id.btn_transporte_avancar);
        totalAviaoTransporteTextView = findViewById(R.id.total_aviao_transporte);
        cardCarro = findViewById(R.id.card_carro);
        cardAviao = findViewById(R.id.card_aviao);
        spinner = (Spinner) findViewById(R.id.transporte_spinner);
        totalCarroTransporteTextView = findViewById(R.id.total_carro_transporte);

        valorPassagemAerea.addTextChangedListener(this);
        valorAluguelCarro.addTextChangedListener(this);
        kmPorLitro.addTextChangedListener(this);
        custoPorLitro.addTextChangedListener(this);
        totalVeiculos.addTextChangedListener(this);
        kmTotal.addTextChangedListener(this);
        //teste

        getSharedPreferencesData();
        getTransporteArrayData();

        setDestinoView();

        obterDados();

        btnAdicionar.setOnClickListener( x -> adicionar());

        btnAvancar.setOnClickListener(x -> {
            if (!adicionouTransporte) {
                Toast.makeText(TransporteActivity.this, "Por favor adicione um meio de transporte!", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(TransporteActivity.this, EntretenimentoActivity.class);
            startActivity(intent);
        });

        checkBox.setOnCheckedChangeListener(this);
    }


    private void obterDados() {
        if(idHome == -1){
            return;
        }

        TransporteDAO transporteDAO = new TransporteDAO(TransporteActivity.this);
        TransporteModel model = transporteDAO.findByIdHome(idHome);

        if(model == null){
            return;
        }


        if(model.getIdAviaoTransporte() > 0){
            AviaoTransporteDAO aviaoTransporteDAO = new AviaoTransporteDAO(TransporteActivity.this);
            AviaoTransporteModel aviaoTransporteModel = aviaoTransporteDAO.findById(model.getIdAviaoTransporte());

            if(aviaoTransporteModel == null){
                return;
            }

            valorPassagemAerea.setText(String.valueOf(aviaoTransporteModel.getValorPassagem()));

            spinner.setSelection(0);
            adicionouTransporte = true;
        }

        if(model.getIdCarroTransporte() > 0){
            CarroTransporteDAO carroTransporteDAO = new CarroTransporteDAO(TransporteActivity.this);
            CarroTransporteModel carroTransporteModel = carroTransporteDAO.findById(model.getIdCarroTransporte());

            if(carroTransporteModel == null){
                return;
            }

            double valorAluguel = carroTransporteModel.getValorAluguelCarro();

            valorAluguelCarro.setText(String.valueOf(valorAluguel));
            checkBox.setChecked(valorAluguel > 0);

           kmTotal.setText(String.valueOf(carroTransporteModel.getKilometroTotal()));
           kmPorLitro.setText(String.valueOf(carroTransporteModel.getKmLitro()));
           custoPorLitro.setText(String.valueOf(carroTransporteModel.getCustoLitro()));
           totalVeiculos.setText(String.valueOf(carroTransporteModel.getTotalVeiculos()));

           adicionouTransporte = true;

           spinner.setSelection(1);
        }
    }

    private void setDestinoView() {
        destinoTextView = findViewById(R.id.nome_destino);
        String textoDestino = getString(R.string.destino) + " " + destino;
        destinoTextView.setText(textoDestino);
    }

    private void getTransporteArrayData() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.transporte_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    private void getSharedPreferencesData() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(TransporteActivity.this);

        destino = sharedPreferences.getString("destino", "");
        idUsuario = sharedPreferences.getLong("idUsuario", -1);
        qtdPessoas = sharedPreferences.getInt("qtdPessoas", 1);
        idHome = sharedPreferences.getLong("idHome", -1);
    }

    private void adicionar() {
        switch (selectedTransport){
            case 0:
                insertAviaoTransporte();
                adicionouTransporte = true;
                break;
            case 1:
                insertCarroTransporte();
                adicionouTransporte = true;
                break;
        }
    }

    private void insertAviaoTransporte() {
        EditText valorPassagemEditText = findViewById(R.id.valor_passagem_avisao);
        String valorPassagemStr = valorPassagemEditText.getText().toString();

        if (valorPassagemStr.isEmpty()) {
            Toast.makeText(TransporteActivity.this, "Por favor insira o valor!", Toast.LENGTH_SHORT).show();
            return;
        }

        AviaoTransporteModel aviaoTransporteModel = new AviaoTransporteModel(
                parseDouble(valorPassagemStr)
        );

        try {
            AviaoTransporteDAO aviaoTransporteDAO = new AviaoTransporteDAO(TransporteActivity.this);
            idNewAviaoTransporte = aviaoTransporteDAO.insert(aviaoTransporteModel);
        } catch (Exception e) {
            Toast.makeText(TransporteActivity.this, "Erro ao inserir Dados do transporte: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        insertTransporte();
    }

    private void insertTransporte() {
        TransporteModel transporteModel = new TransporteModel(
                idNewAviaoTransporte,
                idNewCarroTransporte,
                idUsuario,
                idHome
                
        );

        try {
            TransporteDAO transporteDAO = new TransporteDAO(TransporteActivity.this);
            transporteDAO.insertOrUpdate(transporteModel);

            Toast.makeText(TransporteActivity.this, "Transporte inserido com sucesso!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(TransporteActivity.this, "Erro ao inserir Dados do transporte: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void insertCarroTransporte() {
        String valorAluguelCarroStr = valorAluguelCarro.getText().toString();
        String kmTotalStr           = kmTotal.getText().toString();
        String kmPorLitroStr        = kmPorLitro.getText().toString();
        String custoPorLitroStr     = custoPorLitro.getText().toString();
        String totalVeiculosStr     = totalVeiculos.getText().toString();

        if (kmTotalStr.isEmpty() || kmPorLitroStr.isEmpty() || custoPorLitroStr.isEmpty() || totalVeiculosStr.isEmpty()) {
            Toast.makeText(TransporteActivity.this, "Por favor insira todos os dados!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (valorAluguelCarroStr.isEmpty() && checkboxAluguel) {
            Toast.makeText(TransporteActivity.this, "Por favor insira o valor do aluguel!", Toast.LENGTH_SHORT).show();
            return;
        }

        double valorAluguel = checkboxAluguel ? parseDouble(valorAluguelCarroStr) : 0;
        double kmTotalTrajeto = parseDouble(kmTotalStr);
        double kmLitro = parseDouble(kmPorLitroStr);
        double custoLitro = parseDouble(custoPorLitroStr);
        int totalDeVeiculos = parseInt(totalVeiculosStr);

        if (kmTotal.getText().toString().isEmpty() ||
                kmPorLitro.getText().toString().isEmpty() ||
                custoPorLitro.getText().toString().isEmpty() ||
                totalVeiculos.getText().toString().isEmpty()) {
            Toast.makeText(TransporteActivity.this, "Por favor insira todos os valores!", Toast.LENGTH_SHORT).show();
        }

        double total = TransporteService.calcularTotal(kmTotalTrajeto, kmLitro, custoLitro, totalDeVeiculos, valorAluguel);

        CarroTransporteModel carroTransporteModel = new CarroTransporteModel(
                valorAluguel,
                kmTotalTrajeto,
                kmLitro,
                custoLitro,
                totalDeVeiculos,
                total
        );

        try {
            CarroTransporteDAO carroTransporteDAO = new CarroTransporteDAO(TransporteActivity.this);
            idNewCarroTransporte = carroTransporteDAO.insert(carroTransporteModel);
        } catch (Exception e) {
            Toast.makeText(TransporteActivity.this, "Erro ao inserir Dados do transporte: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        insertTransporte();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedTransport = position;

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

        try {
            double valorPassagem = parseDouble(s.toString());

            if(valorPassagem < 0 ){
                return;
            }

            double total = valorPassagem * qtdPessoas;
            totalAviaoTransporteTextView.setText("Total: " + Extensions.formatToBRL(total));
        } catch (NumberFormatException e) {
            totalAviaoTransporteTextView.setText("Total: 0.00");
        }
    }
    @Override
    public void afterTextChanged(Editable s) {
        calculateTotal();
    }
    private void calculateTotal() {
        try {
            double valorAluguel = checkboxAluguel ? parseDouble(valorAluguelCarro.getText().toString()) : 0;
            double kmTotalTrajeto = parseDouble(kmTotal.getText().toString());
            double kmLitro = parseDouble(kmPorLitro.getText().toString());
            double custoLitro = parseDouble(custoPorLitro.getText().toString());
            int totalDeVeiculos = parseInt(totalVeiculos.getText().toString());
            double total = TransporteService.calcularTotal(kmTotalTrajeto, kmLitro, custoLitro, totalDeVeiculos, valorAluguel);

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
        checkboxAluguel = isChecked;
        LinearLayout layoutAluguelCarro = findViewById(R.id.layout_aluguel_carro);

        if (isChecked) {
            layoutAluguelCarro.setVisibility(View.VISIBLE);
        } else {
            valorAluguelCarro.setText("");
            layoutAluguelCarro.setVisibility(View.GONE);
        }
    }

}