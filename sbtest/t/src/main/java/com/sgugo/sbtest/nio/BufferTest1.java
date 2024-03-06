package com.sgugo.sbtest.nio;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.Arrays;

public class BufferTest1 {
    public static void main(String[] args) {
        CharBuffer buffer = CharBuffer.allocate(20);
        buffer.put("hello 世界");

        //反转读写模式
        buffer.flip();
        System.out.println(buffer);
        System.out.println(buffer.capacity());
        System.out.println(buffer.remaining());
        //定义数组，数组的大小不能超过实际的capacity大小
        char [] dst = new char[buffer.remaining()];

        //把缓冲区中的数据读到字符数组中
        //批量传输时，大小总是固定的，如果没有指定传输的大小，意味着把数组填满
        CharBuffer remaining = buffer.get(dst);

        System.out.println(Arrays.toString(dst));
    }
}
