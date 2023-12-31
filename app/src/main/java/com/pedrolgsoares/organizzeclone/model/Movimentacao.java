package com.pedrolgsoares.organizzeclone.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.pedrolgsoares.organizzeclone.config.ConfiguracaoFirebase;
import com.pedrolgsoares.organizzeclone.helper.Base64Custom;
import com.pedrolgsoares.organizzeclone.helper.DataCustom;

public class Movimentacao {
    private String categoria;
    private String data;
    private String descricao;
    private Double valor;
    private String tipo;
    private String keyValue;



    public Movimentacao() {
    }

    public void salvar(String data){
        FirebaseAuth autenticacao = ConfiguracaoFirebase.getAutenticacao(); // realiza a comunicação
        String idUsuario = Base64Custom.codificarBase64( autenticacao.getCurrentUser().getEmail() );
        String mesAno = DataCustom.getMesAno( data );
        DatabaseReference firebase = ConfiguracaoFirebase.getFirebaseDatabase();
        firebase.child("movimentacao")
                .child(idUsuario)
                .child(mesAno)
                .push()
                .setValue(this);
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }
}
