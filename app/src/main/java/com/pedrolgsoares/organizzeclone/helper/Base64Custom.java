package com.pedrolgsoares.organizzeclone.helper;

import android.util.Base64;

public class Base64Custom {

    // Definindo como estático para não instanciar esta classe para não utilizar estes métodos

    public static String codificarBase64(String texto){
        return  Base64.encodeToString(texto.getBytes(), Base64.DEFAULT).replaceAll("(\\n|\\r)","");

    }

    public static String decodificarBase64(String texto){
        return new String(Base64.decode(texto,Base64.DEFAULT));
    }
}
