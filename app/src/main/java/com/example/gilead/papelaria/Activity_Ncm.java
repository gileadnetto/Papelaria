package com.example.gilead.papelaria;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.INTERNET;

public class Activity_Ncm extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    String      url;
    TextView    txv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ncm);
        this.setTitle("Busca NCM");

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



        //getDesc();
       // getLogo();

        txv = (TextView) findViewById(R.id.searchView);
        //textview com auto search
        txv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            //faz a busca com cada letra digitada
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.e("asd", s.toString());
                String query = s.toString();
                if (query.length() >= 13) {

                    url = "https://cosmos.bluesoft.com.br/produtos/" + txv.getText();
                    getDesc();
                    getLogo();
                }
                else if(query.length() ==8 ){
                    url = "https://cosmos.bluesoft.com.br/ncms/" + txv.getText();
                    getDescNcm();

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //Log.e("asd",s.toString());
            }
        });

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



//TODO PEGAR DESCRICAO

    public  void getDesc(){

        new Description().execute();
    }
    // Description AsyncTask
    private class Description extends AsyncTask<Void, Void, Void> {

        String desc;
        String titulo;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                // Connect to the web site
                Document document = Jsoup.connect(url).get();
                // Using Elements to get the Meta data

                Elements description = document
                        .select("meta[property=og:description]");
                // Locate the content attribute
                desc = description.attr("content");

                //TODO pegando TITULO
                description = document
                        .select("meta[property=og:title]");
                // Locate the content attribute
                titulo = description.attr("content");

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Set description into TextView
            TextView txttitle = (TextView) findViewById(R.id.titletxt);
            TextView txtdesc = (TextView) findViewById(R.id.desctxt);

            txtdesc.setText(desc);
            txttitle.setText(titulo);

        }
    }

//TODO ____________________________-----------------------------------


//TODO PEGAR LOGO

    public void getLogo(){
        new Logo().execute();
    }
    // Logo AsyncTask
    private class Logo extends AsyncTask<Void, Void, Void> {
        Bitmap bitmap;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                // Connect to the web site
                Document document = Jsoup.connect(url).get();
                // Using Elements to get the class data


                // Elements img = document.select("div[class=header-logo] img[src]");
                // Elements img = document.select("a[class=thumbnail] img[src]");//Produtos codigo de barras

                Elements img = document.select("div[class=thumbnail product-thumbnail] img[src]");//Produtos codigo de barras
                // Locate the src attribute
                String imgSrc = img.attr("src");
                Log.e("IMAGEM",imgSrc);
                // Download image from URL
                InputStream input = new java.net.URL(imgSrc).openStream();

                // Decode Bitmap
                bitmap = BitmapFactory.decodeStream(input);


            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Set downloaded image into ImageView
            ImageView logoimg = (ImageView) findViewById(R.id.logo);
            logoimg.setImageBitmap(bitmap);

        }
    }

//TODO ____________________________-----------------------------------


    private void verificarinternet() {
        boolean ok = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED;
        Log.e("permicao",ok+"");

        if(ok!=true) {

            //boolean ok = ContextCompat.checkSelfPermission(this,INTERNET) == PackageManager.PERMISSION_GRANTED;
            // Se não possui permissão
            if (ContextCompat.checkSelfPermission(this, INTERNET) != PackageManager.PERMISSION_GRANTED) {
                // Verifica se já mostramos o alerta e o usuário negou na 1ª vez.
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET)) {
                    // Caso o usuário tenha negado a permissão anteriormente, e não tenha marcado o check "nunca mais mostre este alerta"
                    // Podemos mostrar um alerta explicando para o usuário porque a permissão é importante.
                } else {
                    // Solicita a permissão
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 0);
                }
            } else {
                // Tudo OK, podemos prosseguir.
            }
        }

    }

    private  void verificar (){
        boolean ok = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        Log.e("Permicao",ok+"");

        if( ContextCompat.checkSelfPermission( this, Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ){

            if( ActivityCompat.shouldShowRequestPermissionRationale( this, Manifest.permission.ACCESS_FINE_LOCATION ) ){
                //  callDialog( "É preciso a permission ACCESS_FINE_LOCATION para apresentação dos eventos locais.", new String[]{Manifest.permission.ACCESS_FINE_LOCATION} );
            }
            else{
                ActivityCompat.requestPermissions( this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},0 );
            }
        }
        else{
            //readMyCurrentCoordinates();
        }
    }


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

                txv.setText(result.getContents());


            }
        }

        else {
            super.onActivityResult(requestCode, resultCode, data);

        }
    }





    public  void getDescNcm(){

        new DescriptionNcm().execute();
    }


    private class DescriptionNcm extends AsyncTask<Void, Void, Void> {

        String desc;
        String titulo;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                // Connect to the web site
                Document document = Jsoup.connect(url).get();
                // Using Elements to get the Meta data

                //Elements description = document.select("a[id=ncm-main-thumbnail]");
                // Locate the content attribute
                //desc = description.attr("content");
               // titulo = document.title();
                //titulo = document.tagName();


                Elements div = document.select("img[title]");
                desc = div.attr("title");

              } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Set description into TextView
            TextView txttitle = (TextView) findViewById(R.id.titletxt);
            TextView txtdesc = (TextView) findViewById(R.id.desctxt);

            txtdesc.setText(desc);
            txttitle.setText("NCM : " + txv.getText());

        }
    }



}




