package com.example.gilead.papelaria;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Gilead on 31/12/2016.
 */

public class Processo extends AsyncTask<String, Void,String>{

    private Context context;
    ProgressDialog dialog;
    DataBaseHelper db;

    public Processo(Context contexto) {
        this.context = contexto;
    }

@Override
protected void onPreExecute(){
    //progressDialog.show(context, "Criando PDF", "Criando arquivo PDF, por favor, aguarde alguns instantes.", true, false);
    dialog = new ProgressDialog(context);
    dialog.setTitle("Criando PDF");
    dialog.setMessage("Criando arquivo PDF, por favor, aguarde alguns instantes.");
    dialog.setIndeterminate(true);
    dialog.show();
        }


protected String doInBackground(String... resultado) {

     ArrayList<String> dados = new ArrayList<>();
    db = new DataBaseHelper(context);
    db.setPdf(dados);

        try{

            String filename = "Estoque";
            FilePDFOperations fop = new FilePDFOperations();
            fop.write(filename, dados);
            if (fop.write(filename , dados)) {

                Log.e("----asdsa-----","PDF CRIADO");
                 //resultado = filename + ".pdf created";


                // Toast.makeText(context,filename + ".pdf created", Toast.LENGTH_SHORT).show();
            } else {
                Log.e("----Erro----","PDF nao CRIADO , I/O error ");
                //resultado = "I/O error";
              //  Toast.makeText(context, "I/O error",Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e){
        Log.e("AsyncTask", e.getMessage());
        }

    onPostExecute();
   // dialog.dismiss();
        return null;
        }


protected void onPostExecute(){
   dialog.dismiss();



   // Log.e("sd==",resultado);
   // Toast.makeText(context, "I/O error",Toast.LENGTH_SHORT).show();
        }
        }