package com.example.gilead.tccpatrimonio;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Gilead on 27/11/2016.
 */

public class ListaRow_Loja_Adapter extends BaseAdapter {
    Activity activity;
    List<Loja> lstLoja;
    LayoutInflater inflater;
    EditText edtEan,edtProduto,edtFornecedor,edtVenda,edtEstoque;

    public ListaRow_Loja_Adapter(Activity activity, List<Loja> lstLoja, EditText edtEan, EditText edtProduto, EditText edtFornecedor, EditText edtVenda , EditText edtEstoque) {
        this.activity = activity;
        this.lstLoja = lstLoja;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.edtEan = edtEan;
        this.edtProduto = edtProduto;
        this.edtVenda = edtVenda;
        this.edtFornecedor = edtFornecedor;
        this.edtEstoque = edtEstoque;
    }

    @Override
    public int getCount() {
        return lstLoja.size();
    }

    @Override
    public Object getItem(int posicao) {//i é a posição
        return  lstLoja.get(posicao);
    }

    @Override
    public long getItemId(int posicao) {
        return lstLoja.get(posicao).getId();
    }

    @Override
     public View getView(final int posicao, final View view, ViewGroup viewGroup) {

        final int pos = posicao;
        View rowView;
        rowView=inflater.inflate(R.layout.row_loja,null);
        final TextView txtRowCodigo , txtRowProduto , txtRowFornecedor ,txtRowPreco ,txtRowEstoque ;

        txtRowCodigo= (TextView)rowView.findViewById(R.id.txtRowCodigo);
        txtRowProduto  = (TextView)rowView.findViewById(R.id.txtRowProduto);
       // txtRowFornecedor = (TextView)rowView.findViewById(R.id.txtFornecedor);
        txtRowPreco = (TextView)rowView.findViewById(R.id.txtRowPreco);
        txtRowEstoque = (TextView)rowView.findViewById(R.id.txtRowEstoque);


        txtRowCodigo.setText(""+ lstLoja.get(posicao).getEan());
        txtRowProduto.setText(""+ lstLoja.get(posicao).getProduto());
      //  txtRowFornecedor.setText(""+ lstLoja.get(posicao).getFornecedor());
        txtRowPreco.setText(""+ lstLoja.get(posicao).getVenda());
        txtRowEstoque.setText(""+ lstLoja.get(posicao).getEstoque());

        if(posicao % 2 == 0){
            rowView.setBackgroundColor(Color.rgb(240,240,240));
        }

        rowView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {


            }




        });


        //TODO acção Remover  no row
        ImageButton btnDeletarRow = (ImageButton)rowView.findViewById(R.id.btnAddRow);
        btnDeletarRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               final DataBaseHelper db = new DataBaseHelper(activity);




                AlertDialog.Builder builder = new AlertDialog.Builder(activity);

                builder.setTitle("Excluindo");
                builder.setMessage("Dejesa excluir ?");

                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        db.deletarLoja(lstLoja.get(pos).getEan());

                        view.setVisibility(View.GONE);
                        lstLoja.remove(getItem(pos));
                        notifyDataSetChanged();

                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();





                //   notes.notifyDataSetChanged();
                // atualizaLista(lstProduto);
            }

        });


        return rowView;
    }
}
