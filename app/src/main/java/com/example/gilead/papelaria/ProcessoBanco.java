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

    private Context context;
    private ProgressDialog mprogressDialog;
    DataBaseHelper db;

    private static final String BASE_URL = "http://192.168.4.3:8084/ServicosWeb/webresources/papelaria/";

    public ProcessoBanco(Context contexto) {
        this.context = contexto;
    }

@Override
protected void onPreExecute(){

    db = new DataBaseHelper(context);

        }


protected String doInBackground(String... resultado) {


        Gson g = new GsonBuilder().registerTypeAdapter(Estoque.class, new EstoqueDec()).create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(g))
                .build();

        Log.e("Retrofit", "Criou retrofit");
        ProdutoService service = retrofit.create(ProdutoService.class);
        Call<ArrayList<Estoque>> produtos = service.getEstoque();

        produtos.enqueue(new Callback<ArrayList<Estoque>>() {


            @Override
            public void onResponse(Call<ArrayList<Estoque>> call, Response<ArrayList<Estoque>> response) {
                if (response.isSuccessful()) {

                    ArrayList<Estoque> estoque = response.body();
                    Log.e("PAssou", " array");
                    int cont = estoque.size();


                    //   Toast.makeText(getApplicationContext(),"Atualizando banco  interno com " + cont +" Produtos" ,Toast.LENGTH_LONG ).show();
                    for (Estoque prod : estoque) {

                        Log.e("user", prod.getEan() + "");

                        Estoque Estoque = new Estoque(prod.getEan(), prod.getProduto(), prod.getFornecedor(), prod.getVenda(), prod.getEstoque());
                        db.addEstoque(Estoque);


                    }
                    //  cont--;


                    //  Toast.makeText(getApplicationContext(),"Adicionado : "+ cont +" Produtos " ,Toast.LENGTH_SHORT ).show();

                } else {


                  //  onPostExecute();
                      Toast.makeText(context,"Error : " + response.code(),Toast.LENGTH_SHORT ).show();

                }
            }

            @Override
            public void onFailure(Call<ArrayList<Estoque>> call, Throwable t) {
                //Toast.makeText(getApplicationContext(),"Erro ao conectar no servidor , falha de conexao " + t.getMessage(),Toast.LENGTH_SHORT ).show();
                //dialog.setMessage("Erro ao Conectar ao servidor");
                onPostExecute();
            }


        });






   // onPostExecute();
   // dialog.dismiss();
        return null;

        }


protected void onPostExecute(){
   mprogressDialog.dismiss();



   // Log.e("sd==",resultado);
   // Toast.makeText(context, "I/O error",Toast.LENGTH_SHORT).show();
        }
        }