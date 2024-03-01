package com.sgugo.sbtest.controller;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    // 1.创建储存在线客户端Socket的集合
    public static List<Socket> onLineSockets = new ArrayList<>();
    public static void main(String[] args) throws Exception {
        //2.创建 ServerSocket，为服务端注册端口
        ServerSocket serverSocket = new ServerSocket(2333);
        System.out.println("聊天室已创建");

        //3. 主线程负责监听客户端发来的连接（死循环反复监听）
        while(true){
            // 3.1 使用ServerSocket对象的accept方法，等待客户端的连接请求
            Socket socket = serverSocket.accept();
            // 3.2 将与客户端连接的Socket加入集合
            onLineSockets.add(socket);
            System.out.println(socket.getRemoteSocketAddress()+"上线了");

            // 4. 获取连接后，将对应的socket通信管道，交给一个独立的子线程对象
            new ServerReaderThread(socket).start();

            // 5. 后续的读数据操作再线程类的run方法中进行
        }
    }
}
