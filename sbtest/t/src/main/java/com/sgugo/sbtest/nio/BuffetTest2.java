package com.sgugo.sbtest.nio;

import java.nio.CharBuffer;

public class BuffetTest2 {
    public static void main(String[] args) {

        CharBuffer buffer1 = CharBuffer.allocate(50);
        buffer1.put("夫吾罐何言蛇出"); //此时position = 7

        //不手动指定的话，就是put后的position，也就是 7
        buffer1.position(3);
        CharBuffer buffer2 = buffer1.slice();

        System.out.println(buffer2.capacity()); //47



    }
}
