package com.pedrolgsoares.organizzeclone.helper;

import java.text.SimpleDateFormat;

public class DataCustom {


    public static String getDataAtual(){
        long dataSistema = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dataAtual = simpleDateFormat.format(dataSistema);
        return dataAtual;
    }
    public static String getMesAno(String data){
        String retornoData[] = data.split("/");
        //String dia = retornoData[0];//dia 23
        String mes = retornoData[1];//mes 01
        String ano = retornoData[2];//ano 2018
        String mesAno = mes + ano;
        return mesAno;
    }
}
