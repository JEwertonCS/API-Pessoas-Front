package br.com.stefanini.desafioapipessoa.utils;

public class Util {

    public static String retiraFormatacaoCpf( String cpf ){
        return cpf.replace(".", "").replace("-","");
    }
}
