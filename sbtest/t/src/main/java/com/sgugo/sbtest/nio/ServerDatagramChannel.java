package com.sgugo.sbtest.nio;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class ServerDatagramChannel {
    public static void main(String[] args) throws Exception {
        // 1. 创建服务端的DatagramChannel，并手动指定端口
        DatagramChannel serverChannel = DatagramChannel.open();
        DatagramSocket socket = serverChannel.socket();
        socket.bind(new InetSocketAddress(12122));

        // 2. 既然是服务端，就设置成非阻塞模式
        serverChannel.configureBlocking(false);

        // 3. 创建Buffer空间，用于接收数据
        ByteBuffer buffer = ByteBuffer.allocate(64);

        // 4. 开始接受数据，既然是服务端，就要创建死循环，反复接收数据
        while(true){
            buffer.clear();
            SocketAddress address = serverChannel.receive(buffer);
            if(address == null){
                // System.out.println("未接收到数据");
                Thread.sleep(2000);
            }else{
                buffer.flip();
                System.out.println("接收到了`"+address+"`发来的数据，数据内容为：");
                while(buffer.hasRemaining()){
                    System.out.print((char)buffer.get());
                }
                System.out.println();
            }
        }
    }
}
