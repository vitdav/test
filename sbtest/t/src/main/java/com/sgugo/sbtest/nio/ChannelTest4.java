package com.sgugo.sbtest.nio;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.Random;

public class ChannelTest4 {
    public static void main(String[] args) throws Exception {
        //1. 创建两个通道
        RandomAccessFile raf1 = new RandomAccessFile("files/1.txt", "rw");
        RandomAccessFile raf2 = new RandomAccessFile("files/3.txt", "rw");
        FileChannel srcChannel = raf1.getChannel(); //提供数据的源通道
        FileChannel destChannel = raf2.getChannel(); //接收数据的目标通道

        // 2. 将srcChannel通道的数据传入到destChannel中
        srcChannel.transferTo(0,srcChannel.size(),destChannel);

        //3. 关闭通道
        srcChannel.close();
        destChannel.close();

        System.out.println("数据传输完成");

    }
}
