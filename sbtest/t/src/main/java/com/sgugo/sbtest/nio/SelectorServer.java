package com.sgugo.sbtest.nio;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class SelectorServer {
    private static ServerSocketChannel ssc;
    public static void main(String[] args) throws Exception {
        // 1. 创建ServerSocketChannel，并绑定端口，设置成非阻塞
        ssc = ServerSocketChannel.open();
        ServerSocket ss = ssc.socket();
        ss.bind(new InetSocketAddress(1234));
        ssc.configureBlocking(false);

        // 2. 创建选择器
        Selector selector = Selector.open();

        // 3. 将ServerSocketChannel 注册到 Selector，关注的是连接操作
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        // 4. 将select()方法放入死循环进行轮询，不断确认Channel的就绪状态
        while(true){
            //4.1 调用select，等待就绪的通道
            int num = selector.select();
            //4.2 判断是否有就绪的状态
            if(num == 0){
                continue; //为0表示没有就绪的状态，直接进行下一次循环
            }
            //4.3 到这里表示有就绪状态，此时获取 SelectedKeys的集合
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keys.iterator();

            //4.4. 迭代SelectedKeys集合
            while(iterator.hasNext()){
                SelectionKey selectionKey = iterator.next();
                //==对获取的selectionKey进行操作类型判断，并根据操作类型进行操作

                // 5. 判断通道是否有连接操作，
                if(selectionKey.isAcceptable()){
                    //5.1 true,表示有新的客户端连接进来，获取selectionKey对应的管道
                    ServerSocketChannel sscTmp = (ServerSocketChannel)selectionKey.channel();
                    //5.2 . 调用accept方法，返回到达服务器的新客户端连接
                    SocketChannel clientChannel = sscTmp.accept();
                    //5.3 连接进来的客户端channel，也要设置为非阻塞模式
                    clientChannel.configureBlocking(false);
                    //5.4 将连接进来的客户端channel，也注册到selector,关注他的读和写操作
                    clientChannel.register(selector,SelectionKey.OP_READ|SelectionKey.OP_WRITE);
                    System.out.println("客户端连接成功");
                }

                // 6. 判断通道是否有数据可读操作
                if(selectionKey.isReadable()){
                    //6.1 true,就绪通道有数据可读
                    //这里通过定义一个类的私有方法读取通道内的数据
                    readDataFromSocket(selectionKey);
                    System.out.println("数据读取完成");
                }

                // 7. 判断通道是否有数据写出..... 等其他操作
                // if(selectionKey.isWritable()){
                //     //"同上，暂略"
                // }

                // 8. 移除完成处理的 SelectionKey
                // iterator.remove();
            }
            iterator.remove();
        }
    }

    // 定方法读取通道内的数据
    private static void readDataFromSocket(SelectionKey selectionKey) throws Exception {
        //1. 获得需要读取数据的SocketChannel
        SocketChannel channel = (SocketChannel)selectionKey.channel();
        //2. 从SocketChannel中读取数据到buffer中
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.clear();
        int count = channel.read(buffer);
        if(count<0){ //提前判断是否有数据可读
            channel.close();
            return;
        }
        //3. 循环将数据读入到一个byte集合中
        ArrayList<Byte> list = new ArrayList<>();
        while(count>0){
            buffer.flip();//切换成读模式
            while(buffer.hasRemaining()){
                list.add(buffer.get());
            }
            buffer.clear();//清空buffer，继续读
            count = channel.read(buffer);
        }

        //4. 将list中的数据打印一下
        byte[] bytes = new byte[list.size()];
        for(int i=0;i<bytes.length;i++){
            bytes[i] = list.get(i);
        }
        System.out.println("客户端发送的信息："+new String(bytes));

        //5.关闭channel
        channel.close();
    }
}
