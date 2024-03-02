package com.sgugo.sbtest.nio;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannel1 {
    public static void main(String[] args) throws Exception {
        // 1. 通过文件创建FileChannel
        RandomAccessFile f1 = new RandomAccessFile("files/1.txt", "rw");
        FileChannel channel = f1.getChannel();

        // 2. 创建buffer（这里使用ByteBuffer)
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        // 3. 读取数据到buffer中
        int res = channel.read(buffer); //返回读到的真正字节数
        while(res != -1){ //不为-1表示有内容
            System.out.println("读取了："+res);

            buffer.flip();// 反转读写模式
            //循环读取buffer里的内容
            while(buffer.hasRemaining()){
                System.out.println((char)buffer.get());
            }
            buffer.clear();
            res = channel.read(buffer);
        }
        f1.close();
        System.out.println("结束");
    }
}
