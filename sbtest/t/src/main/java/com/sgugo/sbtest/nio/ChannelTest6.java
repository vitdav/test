package com.sgugo.sbtest.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ChannelTest6 {
    public static void main(String[] args) throws Exception {

        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.socket().bind(new InetSocketAddress(1234));
        ssc.configureBlocking(false);

        while(true){
            SocketChannel socketChannel = ssc.accept();

            if(socketChannel == null){
                System.out.println("尚未找到连接");
                Thread.sleep(2000);
            }else {
                System.out.println("连接成功");
            }
        }
    }
}
