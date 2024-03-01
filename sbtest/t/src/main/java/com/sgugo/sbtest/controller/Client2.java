package com.sgugo.sbtest.controller;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client2 {
    public static void main(String[] args) throws Exception {
        //1.创建Socket对象，创建的同时就会请求与服务端的连接
        Socket socket = new Socket("127.0.0.1", 2333);

        // 创建一个子线程，负责对接socket的输入流，以便从服务端接收发过来的消息
        new ClientReaderThread(socket).start();

        //2. 从Socket通信管道中获取字节输出流，用来发数据给服务端程序
        OutputStream os = socket.getOutputStream();

        //3. 可以选择将原始的字节流进行包装，推荐：Data流
        DataOutputStream dos = new DataOutputStream(os);

        //4. 创建Scanner，接收用户的输入
        Scanner sc = new Scanner(System.in);

        //5. 创建死循环，可以反复接收用户的输入，并将内容发出
        while(true){
            System.out.println("请输入要发送的内容");
            String msg = sc.nextLine(); //获取用户输入的数据

            // 6. 判断用户输入的数据是否是 `/exit`,是的话就退出
            if("/bye".equals(msg)){
                System.out.println("退出成功");
                dos.close();
                socket.close(); //释放资源
                break;//跳出死循环
            }

            //7. 利用输出流直接写数据，输出流是通信管道创建的，会自动发出
            dos.writeUTF(msg);
            dos.flush(); //每写一次就刷出一次，防止内存堆积
        }
    }
}

