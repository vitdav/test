package com.sgugo.sbtest.socket;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Server {
    public static void main(String[] args) throws Exception {
        System.out.println("----服务端启动成功----");

        //1. 创建ServerSocket对象，为服务的注册端口
        ServerSocket serverSocket = new ServerSocket(8080);

        // 创建要给线程池，负责处理通信管道的任务
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                16, 16, 0, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(8),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );
        //2.创建死循环，反复监听浏览器（客户端）发过来的连接
        while(true){
            Socket socket = serverSocket.accept();
            System.out.println(socket.getRemoteSocketAddress()+"上线了");

            //3. 将接收到的通信管道，封装成一个任务对象，交给线程池处理
            pool.execute(new ServerReaderRunnable(socket));
        }
    }
}
