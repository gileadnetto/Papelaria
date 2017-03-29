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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

public class BuscaCodigoActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    String p;
    List<Estoque> data = new ArrayList();
    DataBaseHelper db;
   ListView listaBusca;
   ArrayList<String> DICAS = new ArrayList<String>();
   AutoCompleteTextView textView;
    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca_cod);

        setTitle("Busca por Codigo");

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
       listaBusca = (ListView) findViewById(R.id.listabusca);


            refreshData();

        //textview com auto search
        search = (EditText)findViewById(R.id.searchView);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            //faz a busca com cada letra digitada
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.e("asd",s.toString());
                String query= s.toString();
                data = db.getPorCodLike(query);
                ListaRow_Estoque_Adapter adapter = new ListaRow_Estoque_Adapter(BuscaCodigoActivity.this, data, null, null,null,null,null);
                listaBusca.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable s) {
                //Log.e("asd",s.toString());
            }
        });
       // m2.setOnQueryTextListener(new SearchFiltro());

      // final SearchView m2 = (SearchView) findViewById(R.id.searchView);

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
            Intent it = new Intent(this,BuscaActivity.class);
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



    private void refreshData(){
        data = db.getAllProduto();
        ListaRow_Estoque_Adapter adapter = new ListaRow_Estoque_Adapter(BuscaCodigoActivity.this, data, null, null,null,null,null); //Sem o Onclick
        listaBusca.setAdapter(adapter);
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
                //Toast.makeText(this,"" + result.getContents() ,Toast.LENGTH_LONG).show();

                search.setText(result.getContents());


            }
        }

        else {
            super.onActivityResult(requestCode, resultCode, data);

        }
    }







    private void mensagemErro(String mensagem) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Atenção!");
        dialog.setMessage(mensagem);
        dialog.setNeutralButton("OK", null);
        // dialog.setPositiveButton("Ok");
        dialog.show();
    }

}




