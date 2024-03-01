package com.sgugo.sbtest.controller;

import com.sgugo.sbtest.ServerReaderThread;

import java.io.DataInputStream;
import java.io.InputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Server {
    public static void main(String[] args) throws Exception {
        //1.创建 ServerSocket，为服务端注册端口
        ServerSocket serverSocket = new ServerSocket(2333);

        //2. 主线程负责监听客户端发来的连接（死循环反复监听）
        while(true){
            // 使用ServerSocket对象的accept方法，等待客户端的连接请求
            Socket socket = serverSocket.accept();
            System.out.println(socket.getRemoteSocketAddress()+"上线了");

            // 3. 获取连接后，将对应的socket通信管道，交给一个独立的子线程对象
            new ServerReaderThread(socket).start();

            // 4. 后续的读数据操作再线程类的run方法中进行
        }
    }
}
