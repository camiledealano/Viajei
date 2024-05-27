package com.devmobile.viajei;

import static com.devmobile.viajei.extensios.Extensions.formatToBRL;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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

public class RelatorioActivity extends AppCompatActivity {

    TextView totalPorPessoa, totalHospedagem, totalTransporte, totalAlimentacao,
            totalEntretenimento, totalViagem, qtdPessoa, qtdNoite, relNomeDestino;
    int qtdPessoas, qtdNoites;
    long idUsuario;
    String destino;
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

        SetTextEditView();

        double totalHospedagem = calcularHospedagem();
        double totalTransporte = calcularTransporte();
        double totalAlimentacao = calcularAlimentacao();
        double totalEntretenimento = calcularTotalEntretenimento();

        calcularTotalPorPessoa(totalHospedagem, totalTransporte, totalAlimentacao, totalEntretenimento);
        calcularTotal(totalHospedagem, totalTransporte, totalAlimentacao, totalEntretenimento);

    }

    private void SetTextEditView() {
        qtdPessoa.setText(Integer.toString(qtdPessoas));
        qtdNoite.setText(Integer.toString(qtdNoites));
        relNomeDestino.setText("Relatório de custos para: " + destino);
    }

    private void calcularTotal(double totalHospedagem, double totalTransporte, double totalAlimentacao, double totalEntretenimento) {
        double total =  (totalHospedagem + totalTransporte + totalAlimentacao + totalEntretenimento);
        totalViagem.setText(formatToBRL(total));
    }

    private void calcularTotalPorPessoa(double totalHospedagem, double totalTransporte, double totalAlimentacao, double totalEntretenimento) {
        double total =  (totalHospedagem + totalTransporte + totalAlimentacao + totalEntretenimento) / qtdPessoas;

        totalPorPessoa.setText(formatToBRL(total));
    }

    private double calcularTotalEntretenimento() {
        EntretenimentoDAO entretenimentoDAO = new EntretenimentoDAO(RelatorioActivity.this);
        List<EntretenimentoModel> entretenimentoModelList =  entretenimentoDAO.findByIdUsuario(idUsuario);

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
        AlimentacaoDAO alimentacaoDAO = new AlimentacaoDAO(RelatorioActivity.this);
        AlimentacaoModel AlimentacaoModel = alimentacaoDAO.FindByIdUsuario(idUsuario);

        if (AlimentacaoModel == null) {
            Toast.makeText(RelatorioActivity.this, "Alimentação não encontrada para o usuário especificado.", Toast.LENGTH_LONG).show();
            return 0;
        }

        double total = AlimentacaoModel.getTotal();
        totalAlimentacao.setText(formatToBRL(total));

        return total;
    }
    private double calcularTransporte() {
        TransporteDAO transporteDAO = new TransporteDAO(RelatorioActivity.this);
        CarroTransporteDAO carroTransporteDAO = new CarroTransporteDAO(RelatorioActivity.this);
        AviaoTransporteDAO aviaoTransporteDAO = new AviaoTransporteDAO(RelatorioActivity.this);

        TransporteModel transporteModel = transporteDAO.findByIdUsuario(idUsuario);

        if(transporteModel == null){
            Toast.makeText(RelatorioActivity.this, "Transporte não encontrado para o usuário especificado.", Toast.LENGTH_LONG).show();
            return 0;
        }

        CarroTransporteModel carroTransporteModel = carroTransporteDAO.findById(transporteModel.getIdCarroTransporte());
        AviaoTransporteModel aviaoTransporteModel = aviaoTransporteDAO.findById(transporteModel.getIdAviaoTransporte());

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
        HospedagemDAO hospedagemDAO = new HospedagemDAO(RelatorioActivity.this);
        HospedagemModel hospedagemModel = hospedagemDAO.FindByIdUsuario(idUsuario);

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
    }


}