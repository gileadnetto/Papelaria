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

import static java.lang.String.valueOf;

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




        rowView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
                alertDialog.setTitle("Atualizar Dados");
                alertDialog.setMessage("Entre com a atualização");

                LinearLayout layout = new LinearLayout(activity);
                layout.setOrientation(LinearLayout.VERTICAL);

                final EditText ean = new EditText(activity);
                ean.setHint("Codigo de Barras");
                ean.setText(lstEstoque.get(posicao).getEan());
                layout.addView(ean);

                final EditText produto = new EditText(activity);
                produto.setHint("Produto");
                produto.setText(lstEstoque.get(posicao).getProduto());
                layout.addView(produto);

                final EditText preco = new EditText(activity);
                preco.setHint("Preco");
                preco.setText(lstEstoque.get(posicao).getVenda());
                layout.addView(preco);

                final EditText est = new EditText(activity);
                est.setHint("Estoque");
                est.setText(valueOf(lstEstoque.get(posicao).getEstoque()));
                layout.addView(est);

                alertDialog.setView(layout);

                alertDialog.setPositiveButton("Atualizar",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String query1 = ean.getText().toString();
                        String query2 = produto.getText().toString();
                        String query3 = preco.getText().toString();
                        int query4 = Integer.parseInt(est.getText().toString());



                        if (query1.isEmpty()==false && query2.isEmpty()==false && query3.isEmpty()==false && query4!=0) {

                            DataBaseHelper db = new DataBaseHelper(activity);
                            Estoque estoque = new Estoque(query1,query2,lstEstoque.get(posicao).getFornecedor(), query3,query4);
                            db.updateEstoque(estoque);

                            txtRowCodigo.setText(ean.getText().toString());
                            txtRowProduto.setText(produto.getText().toString());
                            txtRowPreco.setText(preco.getText().toString());
                            txtRowEstoque.setText(est.getText().toString());


                            notifyDataSetChanged();
                        }
                        else {

                            AlertDialog.Builder dialogo = new AlertDialog.Builder(activity);
                            dialogo.setTitle("Atenção!");
                            dialogo.setMessage("Algum Campo Vazio");
                            dialogo.setNeutralButton("OK", null);
                            // dialog.setPositiveButton("Ok");
                            dialogo.show();

                        }

                    }

                });

                alertDialog.setNegativeButton("SAIR",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                    }
                });

                alertDialog.show();


                return true;

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
