package com.sgugo.sbtest.nio;

import java.io.*;
import java.nio.channels.FileChannel;

public class NioTest {
    public static void main(String[] args) throws Exception {
        RandomAccessFile raf = new RandomAccessFile("files/1.txt", "rw");
        File file = new File("files/1.txt");


        FileChannel channel1 = raf.getChannel();

        FileInputStream fis = new FileInputStream(file);
        FileChannel channel2 = fis.getChannel();

        FileOutputStream fos = new FileOutputStream(file);
        FileChannel channel3 = fos.getChannel();
    }
}
