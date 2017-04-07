package com.example.gilead.papelaria;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.gilead.papelaria.service.EstoqueDec;
import com.example.gilead.papelaria.service.ProdutoService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Gilead on 31/12/2016.
 */

public class ProcessoBanco extends AsyncTask<String, Void,String>{

    private                         Context context;
    private                         Progress_Msn progresso;
    DataBaseHelper                  db;
    String                          erro;

    private static final String BASE_URL = "http://192.168.4.3:8084/ServicosWeb/webresources/papelaria/";
   //private static String      BASE_URL = "http://gileadtest.ddns.net:8084/ServicosWeb/webresources/papelaria/";


    public ProcessoBanco(Context contexto) {
    this.context = contexto;
    }

@Override
protected void onPreExecute(){

    db = new DataBaseHelper(context);
    progresso = new Progress_Msn();
    progresso.iniciarProgresso(context);
    progresso.setMessage("Iniciando");

        }


protected String doInBackground(String... resultado) {

                db.dropar();

        Gson g = new GsonBuilder().registerTypeAdapter(Estoque.class, new EstoqueDec()).create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(g))
                .build();

        Log.e("Retrofit", "Criou retrofit");

       final  ProdutoService service = retrofit.create(ProdutoService.class);


         Call<ArrayList<Estoque>> produtos = service.getEstoque();


                try {

                    ArrayList<Estoque> listaResponse =  produtos.execute().body();

                    int cont = listaResponse.size();

                    progresso.setMax(cont);
                    for (Estoque prod : listaResponse) {
                        Log.e("barra :", prod.getEan() + "");
                        Estoque estoque = new Estoque(prod.getEan(), prod.getProduto(), prod.getFornecedor(), prod.getVenda(), prod.getEstoque());
                        db.addEstoque(estoque);
                        progresso.incrementar(1);

                    }
                    onPostExecute();


                } catch (IOException e) {
                   Log.e("Erro",e.toString());
                   erro = e.toString();

                   // progresso.setMessage("Error :" + erro );

                            onPostExecute();


                    e.printStackTrace();
                }



   // onPostExecute();
   // dialog.dismiss();
        return null;

        }


protected void onPostExecute(){
    progresso.encerrar();

        }
        }