package com.pedrolgsoares.organizzeclone.helper;

import java.text.SimpleDateFormat;

public class DataCustom {


    public static String getDataAtual(){
        long dataSistema = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dataAtual = simpleDateFormat.format(dataSistema);
        return dataAtual;
    }
}
