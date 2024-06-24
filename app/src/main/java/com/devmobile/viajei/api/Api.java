package com.devmobile.viajei.api;

import com.devmobile.viajei.api.endpoint.ViagemEndPoint;
import com.devmobile.viajei.api.model.Hospedagem;
import com.devmobile.viajei.api.model.Resposta;
import com.devmobile.viajei.api.model.Viagem;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {

    public static final String URL_ROOT = "http://api.genialsaude.com.br/";

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(URL_ROOT)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static void getViagem(int codigoConta, final Callback<ArrayList<Viagem>> callback) {
        ViagemEndPoint endPoint = retrofit.create(ViagemEndPoint.class);
        Call<ArrayList<Viagem>> call = endPoint.getViagemPorConta(codigoConta);
        call.enqueue(callback);
    }

    public static void postViagem(Viagem viagem, final Callback<Resposta> callback) {
        ViagemEndPoint endPoint = retrofit.create(ViagemEndPoint.class);
        Call<Resposta> call = endPoint.postViagem(viagem);
        call.enqueue(callback);
    }
}
