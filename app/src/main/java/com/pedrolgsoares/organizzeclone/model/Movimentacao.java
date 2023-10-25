package com.pedrolgsoares.organizzeclone.model;

public class Movimentacao {
    private String categoria;
    private String data;
    private String descricao;
    private Double valor;

    public Movimentacao() {
    }

    public void salvar(){

    }
    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}
