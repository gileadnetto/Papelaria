package com.example.gilead.tccpatrimonio;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.gilead.tccpatrimonio.service.EstoqueDec;
import com.example.gilead.tccpatrimonio.service.ProdutoService;
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
    //progressDialog.show(context, "Criando PDF", "Criando arquivo PDF, por favor, aguarde alguns instantes.", true, false);

    db = new DataBaseHelper(context);
    mprogressDialog = new ProgressDialog(context);
    mprogressDialog.setCancelable(true);
    mprogressDialog.setMessage("Processando...");
    //define o estilo como horizontal que nesse caso signifca que terá
    //barra de progressão/contagem
    mprogressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    //reseta o progress para zero e em seguida define o valor máximo
    mprogressDialog.setProgress(0);
    mprogressDialog.setMax(4000); //esse valor deverá ser definido de acordo com sua necessidade
    //apresnta o progressbar
    mprogressDialog.show();

        }


protected String doInBackground(String... resultado) {

mprogressDialog.incrementProgressBy(991);
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
                    mprogressDialog.setMax(cont);


                    //   Toast.makeText(getApplicationContext(),"Atualizando banco  interno com " + cont +" Produtos" ,Toast.LENGTH_LONG ).show();
                    for (Estoque prod : estoque) {

                        Log.e("user", prod.getEan() + "");

                        Estoque Estoque = new Estoque(prod.getEan(), prod.getProduto(), prod.getFornecedor(), prod.getVenda(), prod.getEstoque());
                        db.addEstoque(Estoque);
                       mprogressDialog.incrementProgressBy(1);

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