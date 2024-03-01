package com.sgugo.sbtest.socket;

import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

public class ServerReaderRunnable implements Runnable{
    private Socket socket;
    public ServerReaderRunnable(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run(){
        //直接立即响应一个网页内容给浏览器
        try {
            OutputStream os = socket.getOutputStream();
            //将字节输出流包装成打印流，方便一行行输出
            PrintStream ps = new PrintStream(os);
            ps.println("HTTP/1.1 200 OK");
            ps.println("Content-Type:text/html;charset=UTF-8");
            ps.println(); //必须进行换行
            ps.println("<div style='color:red'>Hello Word</div>");

            //网页显示玩，本次通信就结束了，直接断开连接，释放流
            //网页请求属于短链接，无需保留通信管道
            ps.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
