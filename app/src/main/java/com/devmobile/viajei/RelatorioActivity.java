package com.devmobile.viajei;

import static com.devmobile.viajei.extensios.Extensions.formatToBRL;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.devmobile.viajei.api.Api;
import com.devmobile.viajei.api.model.Resposta;
import com.devmobile.viajei.api.model.Viagem;
import com.devmobile.viajei.api.model.mapper.ViagemMapper;
import com.devmobile.viajei.database.dao.AlimentacaoDAO;
import com.devmobile.viajei.database.dao.AviaoTransporteDAO;
import com.devmobile.viajei.database.dao.CarroTransporteDAO;
import com.devmobile.viajei.database.dao.EntretenimentoDAO;
import com.devmobile.viajei.database.dao.HospedagemDAO;
import com.devmobile.viajei.database.dao.TransporteDAO;
import com.devmobile.viajei.database.model.AlimentacaoModel;
import com.devmobile.viajei.database.model.AviaoTransporteModel;
import com.devmobile.viajei.database.model.CarroTransporteModel;
import com.devmobile.viajei.database.model.EntretenimentoModel;
import com.devmobile.viajei.database.model.HospedagemModel;
import com.devmobile.viajei.database.model.TransporteModel;

import java.math.BigDecimal;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RelatorioActivity extends AppCompatActivity {

    TextView totalPorPessoa, totalHospedagem, totalTransporte, totalAlimentacao,
            totalEntretenimento, totalViagem, qtdPessoa, qtdNoite, relNomeDestino;
    int qtdPessoas, qtdNoites;
    long idUsuario, idHome;
    double total, totalPessoa;
    String destino;
    List<EntretenimentoModel> entretenimentoModelList;
    AlimentacaoModel alimentacaoModel;
    TransporteModel transporteModel;
    AviaoTransporteModel aviaoTransporteModel;
    CarroTransporteModel carroTransporteModel;
    HospedagemModel hospedagemModel;
    Button btnHome;

    private static int CODIGOCONTA = 131469;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_relatorio);

        getSharedPreferencesData();

        totalPorPessoa = findViewById(R.id.rel_total_pessoa);
        totalHospedagem = findViewById(R.id.rel_total_hospedagem);
        totalTransporte = findViewById(R.id.rel_total_transporte);
        totalAlimentacao = findViewById(R.id.rel_total_alimentacao);
        totalEntretenimento = findViewById(R.id.rel_total_entretenimento);
        totalViagem = findViewById(R.id.rel_total_viagem);
        qtdPessoa = findViewById(R.id.rel_qtd_pessoa);
        qtdNoite = findViewById(R.id.rel_qtd_noites);
        relNomeDestino = findViewById(R.id.rel_nome_destino);
        btnHome = findViewById(R.id.btn_home);

        EntretenimentoDAO entretenimentoDAO = new EntretenimentoDAO(RelatorioActivity.this);
        AlimentacaoDAO alimentacaoDAO = new AlimentacaoDAO(RelatorioActivity.this);
        TransporteDAO transporteDAO = new TransporteDAO(RelatorioActivity.this);
        CarroTransporteDAO carroTransporteDAO = new CarroTransporteDAO(RelatorioActivity.this);
        AviaoTransporteDAO aviaoTransporteDAO = new AviaoTransporteDAO(RelatorioActivity.this);
        HospedagemDAO hospedagemDAO = new HospedagemDAO(RelatorioActivity.this);

        entretenimentoModelList =  entretenimentoDAO.findByIdHome(idUsuario);
        alimentacaoModel = alimentacaoDAO.findByIdHome(idHome);
        transporteModel = transporteDAO.findByIdHome(idHome);
        carroTransporteModel = carroTransporteDAO.findById(transporteModel.getIdCarroTransporte());
        aviaoTransporteModel = aviaoTransporteDAO.findById(transporteModel.getIdAviaoTransporte());
        hospedagemModel = hospedagemDAO.findByIdHome(idHome);

        SetTextEditView();

        double totalHospedagem = calcularHospedagem();
        double totalTransporte = calcularTransporte();
        double totalAlimentacao = calcularAlimentacao();
        double totalEntretenimento = calcularTotalEntretenimento();

        calcularTotalPorPessoa(totalHospedagem, totalTransporte, totalAlimentacao, totalEntretenimento);
        calcularTotal(totalHospedagem, totalTransporte, totalAlimentacao, totalEntretenimento);

        Viagem viagem = ViagemMapper.Mapper(alimentacaoModel, aviaoTransporteModel, carroTransporteModel, entretenimentoModelList, hospedagemModel);
        viagem.setTotalViajantes(qtdPessoas);
        viagem.setDuracaoViagem(qtdNoites);
        viagem.setCustoTotalViagem(total);
        viagem.setCustoPorPessoa(totalPessoa);
        viagem.setLocal(destino);
        viagem.setIdConta(CODIGOCONTA);

        Api.postViagem(viagem, new Callback<Resposta>() {
            @Override
            public void onResponse(Call<Resposta> call, Response<Resposta> response) {
                if (response != null && response.isSuccessful()) {
                    Resposta r = response.body();
                    AlertDialog.Builder builder = new AlertDialog.Builder(RelatorioActivity.this);
                    builder.setMessage(r.getMensagem());
                    builder.create().show();
                }
            }

            @Override
            public void onFailure(Call<Resposta> call, Throwable t) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RelatorioActivity.this);
                builder.setMessage(t.getMessage());
                builder.create().show();
            }
        });

        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(RelatorioActivity.this, HomeActivity.class);
            startActivity(intent);
        });

    }

    private void SetTextEditView() {
        qtdPessoa.setText(Integer.toString(qtdPessoas));
        qtdNoite.setText(Integer.toString(qtdNoites));
        relNomeDestino.setText("Relatório de custos para: " + destino);
    }
    private void calcularTotal(double totalHospedagem, double totalTransporte, double totalAlimentacao, double totalEntretenimento) {
        total =  (totalHospedagem + totalTransporte + totalAlimentacao + totalEntretenimento);
        totalViagem.setText(formatToBRL(total));
    }
    private void calcularTotalPorPessoa(double totalHospedagem, double totalTransporte, double totalAlimentacao, double totalEntretenimento) {
        totalPessoa =  (totalHospedagem + totalTransporte + totalAlimentacao + totalEntretenimento) / qtdPessoas;

        totalPorPessoa.setText(formatToBRL(totalPessoa));
    }
    private double calcularTotalEntretenimento() {
        if (entretenimentoModelList == null || entretenimentoModelList.isEmpty()) {
            Toast.makeText(RelatorioActivity.this, "Nenhum entretenimento encontrado para o usuário especificado.", Toast.LENGTH_LONG).show();
            return 0;
        }

        BigDecimal total = entretenimentoModelList.stream()
                                                  .map(EntretenimentoModel::getTotal)
                                                  .reduce(BigDecimal.ZERO, BigDecimal::add);

        totalEntretenimento.setText(formatToBRL(total.doubleValue()));

        return total.doubleValue();
    }
    private double calcularAlimentacao() {
        if (alimentacaoModel == null) {
            Toast.makeText(RelatorioActivity.this, "Alimentação não encontrada para o usuário especificado.", Toast.LENGTH_LONG).show();
            return 0;
        }

        double total = alimentacaoModel.getTotal();
        totalAlimentacao.setText(formatToBRL(total));

        return total;
    }
    private double calcularTransporte() {
        if(transporteModel == null){
            Toast.makeText(RelatorioActivity.this, "Transporte não encontrado para o usuário especificado.", Toast.LENGTH_LONG).show();
            return 0;
        }
        
        double total = 0;
        if(carroTransporteModel != null){
            total = carroTransporteModel.getTotal();
        }

        if(aviaoTransporteModel != null){
            total+= aviaoTransporteModel.getValorPassagem();
        }

        totalTransporte.setText(formatToBRL(total));

        return total;
    }
    private double calcularHospedagem() {

        if (hospedagemModel == null) {
            Toast.makeText(RelatorioActivity.this, "Hospedagem não encontrada para o usuário especificado.", Toast.LENGTH_LONG).show();
            return 0;
        }

        double total = hospedagemModel.getTotal();
        totalHospedagem.setText(formatToBRL(total));
        return total;
    }
    private void getSharedPreferencesData() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(RelatorioActivity.this);

        destino = sharedPreferences.getString("destino", "");
        idUsuario = sharedPreferences.getLong("idUsuario", -1);
        qtdPessoas = sharedPreferences.getInt("qtdPessoas", 1);
        qtdNoites = sharedPreferences.getInt("qtdNoites", 0);
        idHome = sharedPreferences.getLong("idHome", -1);
    }


}