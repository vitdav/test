package com.sgugo.jvm;

public class Demo01 {
    public static void main(String[] args) {
        boolean a = false;
        if(a){
            System.out.println("a为true");
        }else{
            System.out.println("a为false");
        }

        if(a == true){
            System.out.println("a为true");
        }else{
            System.out.println("a为false");
        }
    }
}