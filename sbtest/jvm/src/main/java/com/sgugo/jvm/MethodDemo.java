package com.sgugo.jvm;

public class MethodDemo {
    public static void main(String[] args) {
        study();
    }

    public static void study(){
        eat();

        sleep();
    }

    public static void eat(){
        System.out.println("吃饭");
    }

    public static void sleep(){
        System.out.println("睡觉");
    }
}