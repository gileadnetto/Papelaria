package com.example.gilead.papelaria;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by Cpd on 23/03/2017.
 */

public class Progress_Msn {
    private ProgressDialog mprogressDialog;

    public void iniciarProgresso(Context com){
        mprogressDialog = new ProgressDialog(com);
        mprogressDialog.setCancelable(true);
        mprogressDialog.setMessage("Processando...");
        mprogressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);//define o estilo como horizontal que nesse caso signifca que terá barra de progressão/contagem
        mprogressDialog.setProgress(0);//reseta o progress para zero e em seguida define o valor máximo
        mprogressDialog.setMax(00); //esse valor deverá ser definido de acordo com sua necessidade
        mprogressDialog.show();//apresenta o progressbar

    }

    public void incrementar(int inc){

        mprogressDialog.incrementProgressBy(inc);
    }

    public void  encerrar(){

        if (mprogressDialog.isShowing())
            mprogressDialog.dismiss();
    }

    public void setMax(int cont) {

        mprogressDialog.setMax(cont);
    }

    public void setMessage(String msg){

        mprogressDialog.setMessage(msg);
    }
}
