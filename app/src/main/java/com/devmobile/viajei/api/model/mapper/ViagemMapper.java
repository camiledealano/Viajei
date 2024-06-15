package com.devmobile.viajei.api.model.mapper;

import com.devmobile.viajei.api.model.Aereo;
import com.devmobile.viajei.api.model.Entretenimento;
import com.devmobile.viajei.api.model.Gasolina;
import com.devmobile.viajei.api.model.Hospedagem;
import com.devmobile.viajei.api.model.Refeicao;
import com.devmobile.viajei.api.model.Viagem;
import com.devmobile.viajei.database.model.AlimentacaoModel;
import com.devmobile.viajei.database.model.AviaoTransporteModel;
import com.devmobile.viajei.database.model.CarroTransporteModel;
import com.devmobile.viajei.database.model.EntretenimentoModel;
import com.devmobile.viajei.database.model.HospedagemModel;
import com.devmobile.viajei.database.model.TransporteModel;

import java.util.ArrayList;
import java.util.List;

public class ViagemMapper {
    private static int CODIGOCONTA = 131469;

    public static Viagem Mapper(AlimentacaoModel alimentacaoModel, AviaoTransporteModel aviaoTransporteModel,
                         CarroTransporteModel carroTransporteModel, List<EntretenimentoModel> entretenimentoModel,
                         HospedagemModel hospedagemModel){

        Viagem viagem = new Viagem();

        if(alimentacaoModel != null){
            viagem.setRefeicao(new Refeicao(alimentacaoModel.getCustoMedio(), alimentacaoModel.getQtdRefeicoes(), CODIGOCONTA));
        }

        if(aviaoTransporteModel != null){
            viagem.setAereo(new Aereo(aviaoTransporteModel.getValorPassagem(), 0, CODIGOCONTA));
        }

        if(carroTransporteModel != null){
            viagem.setGasolina(new Gasolina((int) carroTransporteModel.getKilometroTotal(), carroTransporteModel.getKmLitro(),
                    carroTransporteModel.getCustoLitro(), carroTransporteModel.getTotalVeiculos(), CODIGOCONTA));
        }

        if(hospedagemModel != null){
            viagem.setHospedagem(new Hospedagem(hospedagemModel.getCustoMedio(),hospedagemModel.getQtdNoites(),hospedagemModel.getQtdQuartos(), CODIGOCONTA));
        }

        if(!entretenimentoModel.isEmpty()){
            viagem.setListaEntretenimento(mapperEntretenimento(entretenimentoModel));
        }

        return viagem;
    }

    private static List<Entretenimento> mapperEntretenimento(List<EntretenimentoModel> entretenimentoModelList){
        List<Entretenimento> entretenimentos = new ArrayList<Entretenimento>();

        entretenimentoModelList.forEach(x ->
                entretenimentos.add(new Entretenimento(x.getValor().doubleValue(), x.getNomeLazer(), CODIGOCONTA))
        );

        return entretenimentos;
    }
}
