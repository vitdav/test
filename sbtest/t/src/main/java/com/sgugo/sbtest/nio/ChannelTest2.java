package com.sgugo.sbtest.nio;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

public class ChannelTest2 {
    public static void main(String[] args) throws Exception {
        //1. 获取FileChannel（通过RandomAccessFile获取）
        RandomAccessFile raf = new RandomAccessFile("files/02.txt", "rw");
        FileChannel channel = raf.getChannel();

        //2.创建buffer对象（分配空间）
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        //3.准备向buffer写入数据
        String data = "男人，什么罐头我说，曼巴出去";
        buffer.clear(); //先清空buffer

        //4. 将数据放入buffer，这个数据是在内存中创建的，放入同样是内存空间的Buffer
        buffer.put(data.getBytes());
        buffer.flip(); //转换buffer的读写模式

        //5. 将buffer中的数据写入到文件
        while(buffer.hasRemaining()){
            channel.write(buffer);
        }

        //6. 关闭channel
        channel.close();
    }
}
