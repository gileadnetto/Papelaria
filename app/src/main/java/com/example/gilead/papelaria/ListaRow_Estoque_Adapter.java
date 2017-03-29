package com.example.gilead.papelaria;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Gilead on 27/11/2016.
 */

public class ListaRow_Estoque_Adapter extends BaseAdapter {
    Activity activity;
    List<Estoque> lstEstoque;
    LayoutInflater inflater;
    EditText edtEan,edtProduto,edtFornecedor,edtVenda,edtEstoque;

    public ListaRow_Estoque_Adapter(Activity activity, List<Estoque> lstEstoque, EditText edtEan, EditText edtProduto, EditText edtFornecedor, EditText edtVenda , EditText edtEstoque) {
        this.activity = activity;
        this.lstEstoque = lstEstoque;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.edtEan = edtEan;
        this.edtProduto = edtProduto;
        this.edtVenda = edtVenda;
        this.edtFornecedor = edtFornecedor;
        this.edtEstoque = edtEstoque;
    }

    @Override
    public int getCount() {
        return lstEstoque.size();
    }

    @Override
    public Object getItem(int posicao) {//i é a posição
        return  lstEstoque.get(posicao);
    }

    @Override
    public long getItemId(int posicao) {
        return lstEstoque.get(posicao).getId();
    }

    @Override
     public View getView(final int posicao, final View view, ViewGroup viewGroup) {

        final int pos = posicao;
        View rowView;
        rowView=inflater.inflate(R.layout.row,null);
        final TextView txtRowCodigo , txtRowProduto , txtRowFornecedor ,txtRowPreco ,txtRowEstoque ;

        txtRowCodigo= (TextView)rowView.findViewById(R.id.txtRowCodigo);
        txtRowProduto  = (TextView)rowView.findViewById(R.id.txtRowProduto);
       // txtRowFornecedor = (TextView)rowView.findViewById(R.id.txtFornecedor);
        txtRowPreco = (TextView)rowView.findViewById(R.id.txtRowPreco);
        txtRowEstoque = (TextView)rowView.findViewById(R.id.txtRowEstoque);


        txtRowCodigo.setText(""+ lstEstoque.get(posicao).getEan());
        txtRowProduto.setText(""+ lstEstoque.get(posicao).getProduto());
      //  txtRowFornecedor.setText(""+ lstLoja.get(posicao).getFornecedor());
        txtRowPreco.setText(""+ lstEstoque.get(posicao).getVenda());
        txtRowEstoque.setText(""+lstEstoque.get(posicao).getEstoque());

        if(posicao % 2 == 0){
            rowView.setBackgroundColor(Color.rgb(240,240,240));
        }

        rowView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {


            }




        });


        //TODO acção Add  no row
        ImageButton btnDeletarRow = (ImageButton)rowView.findViewById(R.id.btnAddRow);
        btnDeletarRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DataBaseHelper db = new DataBaseHelper(activity);


                boolean resultado =db.buscaLoja(lstEstoque.get(posicao).getEan());
                Log.e("Resultado" , resultado + "");

                if (resultado==false) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);

                    builder.setTitle("Adicionar !!!");
                    builder.setMessage("Deseja Adicionar o Produto no Estoque");

                    builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            Log.e("EAN ",lstEstoque.get(posicao).getEan());

                                Loja loja = new Loja(lstEstoque.get(posicao).getEan(), lstEstoque.get(posicao).getProduto(), lstEstoque.get(posicao).getFornecedor(), lstEstoque.get(posicao).getVenda(), lstEstoque.get(posicao).getEstoque());
                                db.addLoja(loja);

                            dialog.dismiss();
                        }
                    });

                    builder.setNegativeButton("Nao", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            // Do nothing
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();





                }

                else{


                        AlertDialog.Builder dialog2 = new AlertDialog.Builder(activity);
                        dialog2.setTitle("Atenção!");
                        dialog2.setMessage("Produto Ja Cadastrado");
                        dialog2.setNeutralButton("OK", null);
                        dialog2.show();


                }

                notifyDataSetChanged();
                //   notes.notifyDataSetChanged();
                // atualizaLista(lstProduto);

            }

        });


        return rowView;
    }
}
