package com.pedrolgsoares.organizzeclone.config;

import com.google.firebase.auth.FirebaseAuth;

public class ConfiguracaoFirebase {
    private static FirebaseAuth autenticacao;

    // retorna a instancia do FirebaseAuth
    public static FirebaseAuth getAutenticacao(){
        if (autenticacao == null){
            autenticacao = FirebaseAuth.getInstance();
        }
        return autenticacao;
    }
}
