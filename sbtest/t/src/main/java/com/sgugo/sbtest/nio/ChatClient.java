package com.sgugo.sbtest.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Scanner;

/**
 * 聊天室客户端
 */
public class ChatClient {
    // public static void main(String[] args) throws IOException {
    //
    // }

    /**
     * 启动客户端的方法
     */
    public void startClient(String name) throws IOException {
        //1. 创建channel，连接服务器
        SocketChannel channel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8000));

        // 2. 接收服务器响应的消数据
        //2.1 将当前客户端的channel的读取状态注册到Selector
        Selector selector = Selector.open();
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_READ);
        //2.2 创建子线程，用来接收消息，主线程main是用来发送消息的
        //这里采用新建一个线程类进行处理
        new Thread(new ClientThread(selector)).start();

        // 3. 向服务器端发送消息
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNextLine()){
            String msg = scanner.nextLine();
            if(msg.length()>0){
                channel.write(Charset.forName("UTF-8").encode(name+": "+msg));
            }
        }




    }
}
