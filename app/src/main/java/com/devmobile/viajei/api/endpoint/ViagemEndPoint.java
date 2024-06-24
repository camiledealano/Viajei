package com.devmobile.viajei.api.endpoint;

import com.devmobile.viajei.api.model.Resposta;
import com.devmobile.viajei.api.model.Viagem;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ViagemEndPoint {
    @GET("/api/listar/viagem/conta")
    Call<ArrayList<Viagem>> getViagemPorConta(@Query("contaId") int contaId);

    @POST("/api/cadastro/viagem")
    Call<Resposta> postViagem(@Body Viagem viagem);
}
