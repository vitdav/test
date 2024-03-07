package com.sgugo.sbtest.nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

public class ChannelTest1 {
    public static void main(String[] args) throws Exception {

        // 1. 获取FileChannel
        File file = new File("files/1.txt");
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        FileChannel channel = raf.getChannel();

        // 2. 把channel中的数据映射到虚拟内存中
        MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_WRITE,0,file.length());

        // 3. 设置channel的position属性，复制到channel中时，从末尾写入
        channel.position(file.length());

        // 4. 将buffer中的数据写到channel中，完成文件的一次自我复制
        channel.write(buffer);


    }
}
