package com.sgugo.sbtest.nio;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.StandardCharsets;

public class ClientDatagramChannel {
    public static void main(String[] args) throws Exception {
        // 1. 创建客户端的DatagramChannel，不接收数据，无需指定端口
        DatagramChannel clientChannel = DatagramChannel.open();

        // 2. 指定服务端的地址（ip+port)
        InetSocketAddress remoteAddress = new InetSocketAddress("127.0.0.1", 12122);


        //3. 创建buffer，定义要发送的数据
        String message = "Hello, I'm Client";
        ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());
        int n = clientChannel.send(buffer, remoteAddress);
        System.out.println("发送的字节数为"+n);

    }
}
