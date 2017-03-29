package com.example.gilead.papelaria;

import java.io.Serializable;

/**
 * Created by Gilead on 26/11/2016.
 */

public class Loja  {

    private String Ean;
    private String Produto;
    private String Fornecedor;
    private String Venda;
    private int Estoque;
    private int ID;


    public Loja(String ean, String produto, String fornecedor, String venda, int estoque) {
        Produto = produto;
        Ean = ean;
        Fornecedor = fornecedor;
        Venda = venda;
        Estoque = estoque;
    }

    public String getProduto() {
        return Produto;
    }

    public void setProduto(String produto) {
        Produto = produto;
    }

    public String getEan() {
        return Ean;
    }

    public void setEan(String ean) {
        Ean = ean;
    }

    public String getFornecedor() {
        return Fornecedor;
    }

    public void setFornecedor(String fornecedor) {
        Fornecedor = fornecedor;
    }

    public String getVenda() {
        return Venda;
    }

    public void setVenda(String venda) {
        Venda = venda;
    }

    public int getEstoque() {
        return Estoque;
    }

    public void setEstoque(int estoque) {
        Estoque = estoque;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public long getId() {
        return ID;
    }
    public void setId(int id) {
        this.ID = id;
    }


    public Loja() {


    }


}
