package com.sgugo.sbtest.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class SelectorClient {
    public static void main(String[] args) throws Exception {
        // 1. 创建Channel，连接服务器，并设置成非阻塞
        SocketChannel sc = SocketChannel.open();
        sc.configureBlocking(false);
        sc.connect(new InetSocketAddress("127.0.0.1",1234));

        // 2. 对连接过程进行判断（网络连接是个过程，且可能失败）
        while(!sc.finishConnect()){
            System.out.println("连接中，请稍等...");
        }

        // 3. 向服务器发送数据，这里采用Scanner
        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.println("输入要发送的内容");
            String writeText = scanner.nextLine();
            ByteBuffer buffer = ByteBuffer.allocate(writeText.length());
            buffer.put(writeText.getBytes());
            // 4. 将数据写入到buffer
            buffer.flip();
            while(buffer.hasRemaining()){
                sc.write(buffer);
            }
            buffer.clear();
        }
    }
}
