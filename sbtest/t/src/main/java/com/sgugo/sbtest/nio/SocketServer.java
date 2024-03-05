package com.sgugo.sbtest.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class SocketServer {
    public static void main(String[] args) throws Exception {
        // 1. 创建ServerSocketChannel，绑定端口
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(7070));

        //设置成非阻塞模式，服务端往往是非阻塞模式
        ssc.configureBlocking(true);

        // 2. 创建死循环，以便循环监听服务端发起的连接，和新消息
        while(true){
            // 3. 调用accept方法，等待连接，连接成功后返回服务端的SocketChannel
            SocketChannel serverChannel = ssc.accept();
            //因为设置了非阻塞模式，后续代码会直接执行，因此要对Channel进行判断
            if(serverChannel == null){
                System.out.println("null");
                Thread.sleep(2000);
            }else {
                // 4. 接收客户端发送过来的数据
                //4.1 创建ByteBuffer（指定空间）
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                //4.2 将数据读入到buffer
                int res = serverChannel.read(buffer);
                System.out.println("接收的数据量 :" + res + " Byte");
                //4.3（可选）反转buffer的读写模式，获取buffer中的数据并打印
                buffer.flip();
                System.out.print("数据内容为：");
                //可以通过获取socket来获取数据发送方的地址
                System.out.println(serverChannel.socket().getRemoteSocketAddress()+": ");
                while (buffer.hasRemaining()) {
                    System.out.print((char) buffer.get());
                }

                // 5.向客户端发送数据
                //5.1 准备要发送的数据
                String message = "hello I'm Service";
                //5.2 将数据移到buffer，（内存到内存，无需传输，没有IO）
                ByteBuffer sendBuffer = ByteBuffer.wrap(message.getBytes());
                //5.3 调用write方法，将数据发送给客户端
                serverChannel.write(sendBuffer);

                // 6. 关闭连接：本次连接操作完，直接关闭
                serverChannel.close();

                System.out.println("\nServer 一次读写结束");
            }

            //非阻塞模式会直接打印这句话，阻塞模式只有在接收到连接后才会打印这句话
            System.out.println("over");
        }
    }
}
