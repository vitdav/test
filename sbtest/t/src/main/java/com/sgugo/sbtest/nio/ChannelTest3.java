package com.sgugo.sbtest.nio;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Random;

public class ChannelTest3 {
    public static void main(String[] args) throws Exception {
        // 1. 获取文件通道，需要两个，一个源文件，一个目标文件
        RandomAccessFile raf1 = new RandomAccessFile("files/02.txt","r");
        RandomAccessFile raf2 = new RandomAccessFile("files/03.txt", "rw");
        FileChannel srcChannel = raf1.getChannel();
        FileChannel destChannel = raf2.getChannel();

        // 2. 分配缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        // 3. 将源文件的数据通过通道读入缓冲区，在将缓冲区的数据通过通道写入到目标文件
        // 并不知道分配的缓冲区是否能存放所有数据，因此要循环读写

        while(true){
            //3.1 每次向buffer写入数据时，都先进行清空
            buffer.clear();
            //3.2 从源文件读取数据
            int res = srcChannel.read(buffer);
            if(res == -1){
                break;
            }
            //3.3 buffer中有数据了，转换buffer的模式为可读模式
            buffer.flip();

            //3.4 将buffer中的数据通过channel
            destChannel.write(buffer);
        }

        //4. 关闭通道
        srcChannel.close();
        destChannel.close();
        System.out.println("复制完成");
    }

}
