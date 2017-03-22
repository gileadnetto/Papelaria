//TODO LEIA

//TODO Cada activity contem 3 layouts
//primeira "activity_*****" la estara o navegador lateral e tbm chhamara outra classe  a
//segunda "app_bar_******' la  ira inclementar  a barra superior e tbm vai incluir o layout principal ou seja a terceira layout
//terceira "content_*******" é o layout  final , la que sera colocado os itens para a interação com o usuario

//TODO Classes
 //BuscaAvtivity tela de busca com um textview q a cada digitação faz a busca no banco sqlite

//DataBaseHelper classe  onde  tem o CRUD das tabelas

//Escola e Estoque classe objeto

//EscolaAdapter  e ProdutoAdaper , classe onde ocorre a interação entre o objeto e o Listview

//Formulario  acho que serar a classe conde sera feita a tranferencia do item  temos q definir isso ainda

//ListaRow_Estoque_Adapter  é uma classe copia da EstoqueAdapter  sem a açao no listView   estava ultilizando na classe Busca pois  dava erro quando  clicava num item  podemos apagar  ou não veremos

//Manter_tab_produto_escola classe onde iremos add os produtos e as escolas , ou seja classe de alimentação ao bonco

//produtoActivyti era a clase de add os itens no banco , ja pode apagar

//Tab1_Produto e Tab2_escola uma classe Fragment  da classe Manter_tab_ .... ou seja  onde iremos manter os produtos e as escolas

//TODO LAYOUTS

// temos as activyti que chama a app_bar que chama a content

//row  é o listView modificado  .. usando nos produtos

//nav_header ... layout pertencente ao menu lateral

//escola_list é o row do listView modificado  usano as listas das  escola






package com.example.gilead.tccpatrimonio;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.gilead.tccpatrimonio.service.EstoqueDec;
import com.example.gilead.tccpatrimonio.service.ProdutoService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.logging.Handler;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String BASE_URL = "http://192.168.4.3:8084/ServicosWeb/webresources/papelaria/";
    //private static final String BASE_URL = "http://192.168.4.3:8080/ServicosWeb/webresources/papelaria/";

    private ProgressDialog mprogressDialog;


    DataBaseHelper db;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        verificarinternet();

       // db = new DataBaseHelper(this);
        //dados = new ArrayList<>();
        //db.setPdf(dados);

        setTitle("Papelaria");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        db = new DataBaseHelper(this); // instanciar para usar a classe DATABASEHelper



     // Loja loja = new Loja("test", "asdasd", "fdfdsf","dasdsadf",323);
       //db.addLoja(loja);


    }

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

        } else if (id == R.id.nav_tela1) {
            setTitle("Busca pod Produto");
            Intent it = new Intent(this,BuscaActivity.class);
            startActivity(it);
           // setContentView(R.layout.activity_main);

        } else if (id == R.id.nav_tela2) {
            setTitle("Busca por Codigo");
            Intent it = new Intent(this, BuscaCodigoActivity.class);
            startActivity(it);
           // setContentView(R.layout.activity_main);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    ///EVENTOS DOS BOTOES
    public void buscarProduto(View v){

        Intent it = new Intent(this,BuscaActivity.class);
        // startActivityForResult(it, 1);
        startActivity(it);
        // setContentView(R.layout.content_busca);
    }

    public void buscarCodigo(View v){

        Intent it = new Intent(this,BuscaCodigoActivity.class);
        // startActivityForResult(it, 1);
        startActivity(it);
        // setContentView(R.layout.content_busca);
    }

    public void estoque(View v){

        Intent it = new Intent(this,Busca_Loja_Activity.class);
        // startActivityForResult(it, 1);
        startActivity(it);
        // setContentView(R.layout.content_busca);
    }


    public void importarr(View v){

       db.dropar();

        //TODO caso contrario

        Gson g = new GsonBuilder().registerTypeAdapter(Estoque.class , new EstoqueDec()).create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(g))
                .build();
        Log.e("Cherff","asd");
        ProdutoService service = retrofit.create(ProdutoService.class);
        Call<ArrayList<Estoque>> produtos = service.getEstoque();

        Toast.makeText(getApplicationContext(),"Conectando ... ",Toast.LENGTH_SHORT ).show();

        produtos.enqueue(new Callback<ArrayList<Estoque>>() {


            @Override
            public void onResponse(Call<ArrayList<Estoque>> call, Response<ArrayList<Estoque>> response) {
                if(response.isSuccessful()){


                    ArrayList<Estoque> user = response.body();
                    int cont= user.size();

                    //   Toast.makeText(getApplicationContext(),"Atualizando banco  interno com " + cont +" Produtos" ,Toast.LENGTH_LONG ).show();
                    for(Estoque prod:user){

                        Log.e("user",prod.getEan()+"");

                            Estoque Estoque = new Estoque(prod.getEan(), prod.getProduto(), prod.getFornecedor(),prod.getVenda(),prod.getEstoque());
                            db.addEstoque(Estoque);

                        }
                        //  cont--;


                    Toast.makeText(getApplicationContext(),"Adicionado : "+ cont +" Produtos " ,Toast.LENGTH_SHORT ).show();


                }else{
                    Toast.makeText(getApplicationContext(),"Error : " + response.code(),Toast.LENGTH_SHORT ).show();}
            }

            @Override
            public void onFailure(Call<ArrayList<Estoque>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Erro ao conectar no servidor , falha de conexao " + t.getMessage(),Toast.LENGTH_SHORT ).show();

            }
        });

        //dialog.dismiss();
      //   **/
    }


    public void criarPdf(View v){

        verificarPermissao();

        new Processo(this).execute();

        // Processo processo = new Processo(this);
        //processo.execute();

        //  Toast.makeText(this, resultado ,Toast.LENGTH_SHORT).show();

    }

    //metodo para dar a permição Write
    private void verificarPermissao() {
        boolean ok = ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        // Se não possui permissão
        if (ContextCompat.checkSelfPermission(this,WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // Verifica se já mostramos o alerta e o usuário negou na 1ª vez.
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, WRITE_EXTERNAL_STORAGE)) {
                // Caso o usuário tenha negado a permissão anteriormente, e não tenha marcado o check "nunca mais mostre este alerta"
                // Podemos mostrar um alerta explicando para o usuário porque a permissão é importante.
            } else {
                // Solicita a permissão
                ActivityCompat.requestPermissions(this,new String[]{WRITE_EXTERNAL_STORAGE},0);
            }
        } else {
            // Tudo OK, podemos prosseguir.
        }



    }

    private void verificarinternet() {
        boolean ok = ContextCompat.checkSelfPermission(this,INTERNET) == PackageManager.PERMISSION_GRANTED;
        // Se não possui permissão
        if (ContextCompat.checkSelfPermission(this,INTERNET) != PackageManager.PERMISSION_GRANTED) {
            // Verifica se já mostramos o alerta e o usuário negou na 1ª vez.
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, INTERNET)) {
                // Caso o usuário tenha negado a permissão anteriormente, e não tenha marcado o check "nunca mais mostre este alerta"
                // Podemos mostrar um alerta explicando para o usuário porque a permissão é importante.
            } else {
                // Solicita a permissão
                ActivityCompat.requestPermissions(this,new String[]{INTERNET},0);
            }
        } else {
            // Tudo OK, podemos prosseguir.
        }



    }

public void importar(View v){
   // new ProcessoBanco(this).execute();
    mprogressDialog = new ProgressDialog(this);
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
               // mprogressDialog.setMax(cont);


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

                Toast.makeText(MainActivity.this,"Error : " + response.code(),Toast.LENGTH_SHORT ).show();

            }
        }

        @Override
        public void onFailure(Call<ArrayList<Estoque>> call, Throwable t) {
            Toast.makeText(getApplicationContext(),"Erro ao conectar no servidor , falha de conexao " + t.getMessage(),Toast.LENGTH_SHORT ).show();
            //dialog.setMessage("Erro ao Conectar ao servidor");

        }


    });

}

    }




