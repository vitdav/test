package com.sgugo.sbtest.gclog;


import java.util.ArrayList;

public class GCTest {
    public static void main(String[] args) throws InterruptedException {
        ArrayList<byte[]> list = new ArrayList<>();

        for(int i = 0; i < 500; i++){
            byte[] arr = new byte[1024 * 100];
            list.add(arr);
            Thread.sleep(10);

        }
    }
}
