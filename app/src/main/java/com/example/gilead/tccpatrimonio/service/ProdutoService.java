package com.example.gilead.tccpatrimonio.service;

import com.example.gilead.tccpatrimonio.Estoque;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface ProdutoService {

    //recebe do banco externo todos os dados do banco
    @GET("get/estoque")
    Call<ArrayList<Estoque>> getEstoque();

    //envia para o banco 1 usuario
    @POST("inserir/")
    Call<Boolean> inserirProduto(@Body Estoque pro);

    //exportar os dados do aplicativo para o banco externo
    @POST("inserirLista")
     Call<String> exportarProduto(@Body String listaProdutoJson);

    //@GET("usuario/get/{primeiro}/{segundo}")
    //Call<Usuario> setDados(@Path("primeiro") int primeiro, @Path("segundo") int segundo);

    //esclui um uruario mandando como parametro um login
    @GET("produto/excluir/{login}")
    Call<String> excluirProduto(@Path("login") String login);
}
