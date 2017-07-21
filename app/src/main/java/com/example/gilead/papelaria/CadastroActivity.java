package com.example.gilead.papelaria;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

public class CadastroActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DataBaseHelper db;

   // @InjectView(R.id.input_codigo) EditText edtCodigo;
   // @InjectView(R.id.input_produto) EditText edtProduto;
   // @InjectView(R.id.input_preco) EditText edtPreco;
   // @InjectView(R.id.input_quantidade) EditText edtQtd;

    EditText                            edtCodigo ;
    EditText                            edtProduto ;
    EditText                            edtPreco ;
    EditText                            edtQtd ;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        setTitle("Adicionar Produto");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

            db = new DataBaseHelper(this);

        edtCodigo = (EditText)findViewById(R.id.input_codigo);
        edtProduto = (EditText)findViewById(R.id.input_produto);
        edtPreco = (EditText)findViewById(R.id.input_preco);
        edtQtd = (EditText)findViewById(R.id.input_quantidade);


    }

    //comando pra abrir o menu lateral
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //comando para adicionar o menu no aplicativo
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);


        return true;
    }





    //navegação do menu lateral
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_inicio) {
            Intent it = new Intent(this,MainActivity.class);
            startActivity(it);

        } else if (id == R.id.nav_tela1) {
            setTitle("Busca pod Produto");
            Intent it = new Intent(this,CadastroActivity.class);
            startActivity(it);


        } else if (id == R.id.nav_tela2) {
            setTitle("Busca por Codigo");
            Intent it = new Intent(this, BuscaCodigoActivity.class);
            startActivity(it);


        } else if (id == R.id.nav_tela3) {
            setTitle("Estoque");
            Intent it = new Intent(this, Busca_Loja_Activity.class);
            startActivity(it);

        } else if (id == R.id.nav_tela4) {
            setTitle("Busca NCM");
            Intent it = new Intent(this, Activity_Ncm.class);
            startActivity(it);


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    //acao do qrcode
    public void escanear(View v){

        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.PRODUCT_CODE_TYPES);
        integrator.setPrompt("Scan");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(true);
        integrator.setBarcodeImageEnabled(false);
        integrator.initiateScan();
    }


    //complemanto do QRCODE
    @Override
    protected void onActivityResult(int requestCode , int resultCode, Intent data){

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

        if(result!=null){
            if(result.getContents()==null){
                Toast.makeText(this,"Voce Cancelou o Escaneamento" ,Toast.LENGTH_LONG).show();

            }
            else{




                String query= result.getContents().toString();

                edtCodigo.setText(query);
                Log.e("tipo" ,query);


            }
        }

        else {
            super.onActivityResult(requestCode, resultCode, data);

        }
    }




    public void cadastrarProduto(View v) {

         String codigo = edtCodigo.getText().toString();
         String produto = edtProduto.getText().toString();
         String preco = "R$" + edtPreco.getText().toString();
         String qtd = edtQtd.getText().toString();

        if(codigo.equals("") ||  produto.equals("") ||  preco.equals("") ||  qtd.equals("") ){

            AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
            dialogo.setTitle("Atenção!");
            dialogo.setMessage("Algum Campo Vazio");
            dialogo.setNeutralButton("OK", null);
            // dialog.setPositiveButton("Ok");
            dialogo.show();

        }

        else {


            boolean resultado = db.buscaLoja(codigo);
            Log.e("Resultado", resultado + "");

            if (resultado == false) {


                Loja loja = new Loja(codigo, produto, "apk", preco, Integer.parseInt(qtd));
                db.addLoja(loja);
                Toast.makeText(this, "Adicionado no Estoque", Toast.LENGTH_SHORT).show();

                edtCodigo.setText(null);
                edtProduto.setText(null);
                edtPreco.setText(null);
                edtQtd.setText(null);


            } else {


                AlertDialog.Builder dialog2 = new AlertDialog.Builder(this);
                dialog2.setTitle("Atenção!");
                dialog2.setMessage("Produto Ja Cadastrado");
                dialog2.setNeutralButton("OK", null);
                dialog2.show();


            }
        }


    }


}




