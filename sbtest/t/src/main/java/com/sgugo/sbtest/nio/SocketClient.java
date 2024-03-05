package com.sgugo.sbtest.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SocketClient {
    public static void main(String[] args) throws Exception {
        // 1. 创建SocketChannel，指定要连接的服务端地址
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 7070);
        SocketChannel clientChannel = SocketChannel.open(address);
        //客户端的读写设置成阻塞模式，否则会产生还未读到数据，代码就执行完了
        //后续有对非阻塞模式的读写处理，这里先暂时使用阻塞模式
        clientChannel.configureBlocking(true);

        // 2. 向服务的发送数据
        //2.1 准备要发送的数据
        String message = "Hello, I'm Client";
        //2.2 将数据移到buffer（内存到内存，无需传输，没有IO）
        ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());
        //2.3 调用write方法，将buffer中的数据发送给服务端
        clientChannel.write(buffer);

        //3. 接收服务的发来的数据
        //3.1 创建接收数据的buffer
        ByteBuffer receiveBuffer = ByteBuffer.allocate(1024);
        //3.2 调用read方法进行接收，此时数据已经在buffer中了
        int res = clientChannel.read(receiveBuffer);

        //3.3（可选）反转buffer，读取buffer中接收的数据
        System.out.println("本次接收的数据量 :"+res+" Byte");
        System.out.print("本次数据内容为：");
        receiveBuffer.flip();
        //可以通过获取socket来获取数据发送方的地址
        System.out.println(clientChannel.socket().getRemoteSocketAddress()+": ");
        while(receiveBuffer.hasRemaining()){
            System.out.print((char)receiveBuffer.get());
        }
        buffer.clear();

        // 4. 关闭连接：本次连接操作完，直接关闭
        clientChannel.close();
        System.out.println("\nClient 读写结束");
    }
}
