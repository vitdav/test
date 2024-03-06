package com.sgugo.sbtest.nio;

import java.nio.CharBuffer;

public class BufferTest1 {
    public static void main(String[] args) {
        // 1. 创建buffer
        CharBuffer buffer = CharBuffer.allocate(5);

        // 2. 创建字符数组（长度为7）
        char[] data = {'夫','吾','罐','何','言','蛇','出'};

        // 3. 将数组中的数据写入缓冲区
        //buffer.put(data); //缓冲区无法储存全部数据，会抛出异常
        //使len = buffer.remaining()，只储存缓冲区可容纳的数据量
        buffer.put(data, 0, buffer.remaining());

        // 4. 反转读写模式，读取缓冲区的数据
        buffer.flip();
        System.out.println(buffer); //夫吾罐何言
    }
}
