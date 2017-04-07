package com.example.gilead.papelaria;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    //DataBASE
    private static final int DATABASE_VER = 1;
    private static final String DATABASE_NAME = "PAPELARIA";

    //tabela papelaria
    private static final String TABLE_NAME = "Papelaria";
    private static final String KEY_EAN = "Ean";
    private static final String KEY_PRODUTO = "Produto";
    private static final String KEY_FORNECEDOR = "Fornecedor";
    private static final String KEY_VENDA = "Venda";
    private static final String KEY_ESTOQUE = "Estoque";

    private static final String TABLE_NAME_LOJA = "Loja";

    private static final String KEY_ID = "id";



    public DataBaseHelper(Context context ) {
        super(context, DATABASE_NAME, null, DATABASE_VER);
    }


    //CRIANDO O BANCO
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE_PAPELARIA = "CREATE TABLE " + TABLE_NAME + "("
                    +KEY_ID +" INT PRIMARY KEY ,"
                    +KEY_EAN +" TEXT ,"
                    +KEY_PRODUTO+" TEXT,"
                    +KEY_FORNECEDOR+" TEXT,"
                    +KEY_VENDA+ " TEXT ,"
                    +KEY_ESTOQUE + " INTEGER "   + ")";


        String CREATE_TABLE_LOJA = "CREATE TABLE " + TABLE_NAME_LOJA + "("
                +KEY_ID +" INT PRIMARY KEY ,"
                +KEY_EAN +" TEXT ,"
                +KEY_PRODUTO+" TEXT,"
                +KEY_FORNECEDOR+" TEXT,"
                +KEY_VENDA+ " TEXT ,"
                +KEY_ESTOQUE + " INTEGER "   + ")";


        db.execSQL(CREATE_TABLE_PAPELARIA);
        db.execSQL(CREATE_TABLE_LOJA);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int in) {
        db.execSQL("DROP TABLE IF EXIST " + TABLE_NAME);
        onCreate(db);

    }


//CRUD dos Produtos

    public void addEstoque(Estoque estoque){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put(KEY_EAN, estoque.getEan());
        values.put(KEY_PRODUTO, estoque.getProduto());
        values.put(KEY_FORNECEDOR, estoque.getFornecedor());
        values.put(KEY_VENDA, estoque.getVenda());
        values.put(KEY_ESTOQUE, estoque.getEstoque());


        db.insert(TABLE_NAME , null ,values);
        db.close();

    }

    public int updateEstoque(Estoque estoque){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put(KEY_EAN, estoque.getEan());
        values.put(KEY_PRODUTO, estoque.getProduto());
        values.put(KEY_FORNECEDOR, estoque.getFornecedor());
        values.put(KEY_VENDA, estoque.getVenda());
        values.put(KEY_ESTOQUE, estoque.getEstoque());


        return db.update(TABLE_NAME , values,KEY_EAN+ " =?" , new String[]{String.valueOf(estoque.getEan())});


    }

    public void deletar (int query){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + KEY_EAN +"="+query );
        db.close();

    }


    public List<Estoque> getAllProduto(){
        List<Estoque> lstEstoque = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        if(cursor.moveToFirst()){

            do{
               Estoque estoque = new Estoque();


               estoque.setEan(cursor.getString(1));
               estoque.setProduto(cursor.getString(2));
               estoque.setFornecedor(cursor.getString(3));
                estoque.setVenda(cursor.getString(4));
               estoque.setEstoque(cursor.getInt(5));


                lstEstoque.add(estoque);

           }
            while(cursor.moveToNext());

        }
        return lstEstoque;
    }



    public boolean buscaEstoque(String query){

        // boolean result=false;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(false,TABLE_NAME, new String[] {KEY_EAN,KEY_PRODUTO, KEY_FORNECEDOR ,KEY_VENDA,KEY_ESTOQUE}, KEY_EAN + " LIKE'%" + query + "%' ",null,null,null,null,null);

        if(cursor.moveToFirst()){

            do{
                if(query == cursor.getString(1)){
                    return true;
                }


            }while(cursor.moveToNext());

        }

        return false;
    }

    public void dropar(){

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
        db.close();
    }

    ///------COPIAS-------


    public List<Estoque> getPorItemLike(String item) throws SQLException {

        List<Estoque> lstEstoque = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(true, TABLE_NAME, new String[]{KEY_EAN, KEY_PRODUTO, KEY_FORNECEDOR, KEY_VENDA, KEY_ESTOQUE}, KEY_PRODUTO + " LIKE'%" + item + "%' ", null, null, null, null, null);

       Log.e("pesquisa" , item);

        if (cursor.moveToFirst()) {

            do {
                Estoque estoque = new Estoque();


                estoque.setEan(cursor.getString(0));
                estoque.setProduto(cursor.getString(1));
                estoque.setFornecedor(cursor.getString(2));
                estoque.setVenda(cursor.getString(3));
                estoque.setEstoque(cursor.getInt(4));


                lstEstoque.add(estoque);

            } while (cursor.moveToNext());

        }
        return lstEstoque;
    }

    public List<Estoque> getPorCodLike(String item) throws SQLException {

        List<Estoque> lstEstoque = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(true, TABLE_NAME, new String[]{KEY_EAN, KEY_PRODUTO, KEY_FORNECEDOR, KEY_VENDA, KEY_ESTOQUE}, KEY_EAN + " LIKE'%" + item + "%' ", null, null, null, null, null);

        if (cursor.moveToFirst()) {

            do {
                Estoque estoque = new Estoque();


                estoque.setEan(cursor.getString(0));
                estoque.setProduto(cursor.getString(1));
                estoque.setFornecedor(cursor.getString(2));
                estoque.setVenda(cursor.getString(3));
                estoque.setEstoque(cursor.getInt(4));


                lstEstoque.add(estoque);

            } while (cursor.moveToNext());

        }
        return lstEstoque;
    }





    //CRUD dos Loja

    public void addLoja(Loja loja){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put(KEY_EAN, loja.getEan());
        values.put(KEY_PRODUTO, loja.getProduto());
        values.put(KEY_FORNECEDOR, loja.getFornecedor());
        values.put(KEY_VENDA, loja.getVenda());
        values.put(KEY_ESTOQUE, loja.getEstoque());


        db.insert(TABLE_NAME_LOJA , null ,values);
        db.close();

    }

    public List<Loja> getAllLoja(){
        List<Loja> lstloja = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME_LOJA;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        if(cursor.moveToFirst()){

            do{
                Loja loja = new Loja();


                loja.setEan(cursor.getString(1));
                loja.setProduto(cursor.getString(2));
                loja.setFornecedor(cursor.getString(3));
                loja.setVenda(cursor.getString(4));
                loja.setEstoque(cursor.getInt(5));


                lstloja.add(loja);

            }
            while(cursor.moveToNext());

        }
        return lstloja;
    }

    public List<Loja> getLojaItem(String item) throws SQLException {

        List<Loja> lstLoja = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(true, TABLE_NAME_LOJA, new String[]{KEY_EAN, KEY_PRODUTO, KEY_FORNECEDOR, KEY_VENDA, KEY_ESTOQUE}, KEY_PRODUTO + " LIKE'%" + item + "%' ", null, null, null, null, null);



        if (cursor.moveToFirst()) {

            do {
                Loja loja = new Loja();


                loja.setEan(cursor.getString(0));
                loja.setProduto(cursor.getString(1));
                loja.setFornecedor(cursor.getString(2));
                loja.setVenda(cursor.getString(3));
                loja.setEstoque(cursor.getInt(4));


                lstLoja.add(loja);

            } while (cursor.moveToNext());

        }
        return lstLoja;
    }

    public void deletarLoja (String query){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME_LOJA + " WHERE " + KEY_EAN +"="+query );
        db.close();

    }

    public boolean buscaLoja(String query){
        Log.e("Query" ,query);

        // boolean result=false;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(false,TABLE_NAME_LOJA, new String[] {KEY_EAN,KEY_PRODUTO, KEY_FORNECEDOR ,KEY_VENDA,KEY_ESTOQUE}, KEY_EAN + " LIKE'%" + query + "%' ",null,null,null,null,null);

        if(cursor.moveToFirst()){

            do{
                if(query.equals(cursor.getString(1))){
                    return true;
                }


            }while(cursor.moveToNext());

        }

        return false;
    }

    public List<String> setPdf(List<String> pdfDados) {

        String selectQuery = "SELECT * FROM " + TABLE_NAME_LOJA + " order by produto";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        if(cursor.moveToFirst()){

            do{

                pdfDados.add(cursor.getString(1));
                pdfDados.add(cursor.getString(2));
                pdfDados.add(cursor.getString(3));
                pdfDados.add(cursor.getString(4));
                pdfDados.add(String.valueOf(cursor.getInt(5)));
            }
            while(cursor.moveToNext());

        }
        return pdfDados;
    }

    public int updateLoja(Loja loja){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put(KEY_EAN, loja.getEan());
        values.put(KEY_PRODUTO, loja.getProduto());
        values.put(KEY_FORNECEDOR, loja.getFornecedor());
        values.put(KEY_VENDA, loja.getVenda());
        values.put(KEY_ESTOQUE, loja.getEstoque());


        return db.update(TABLE_NAME_LOJA , values,KEY_EAN+ " =?" , new String[]{String.valueOf(loja.getEan())});


    }



}





