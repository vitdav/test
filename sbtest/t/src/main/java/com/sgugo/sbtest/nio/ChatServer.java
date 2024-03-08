package com.sgugo.sbtest.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * 聊天室服务端
 */
public class ChatServer {

    //启动服务的主方法
    public static void main(String[] args){
        try {
            new ChatServer().startServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 启动服务器端
     */
    public void startServer() throws IOException {
        // 1. 创建Selector选择器
        Selector selector = Selector.open();

        // 2. 创建ServerSocketChannel通道，并绑定端口，设置成非阻塞模式
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(8000));
        ssc.configureBlocking(false);

        // 3. 注册ServerSocketChannel，监听接入操作的状态
        ssc.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("服务器已启动成功");

        // 4. 循环，等待是否有新的连接接入
        for(;;){
            //4.1 调用select，获取就绪的channel数量
            int num = selector.select();
            //4.2 未获取就直接跳出本次循环
            if(num == 0){
                continue;
            }
            //4.3 获取 SelectedKeys的集合，保护所有就绪的状态
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keys.iterator();

            // 5. 迭代SelectionKey，对每个值进行判断并做处理
            while(iterator.hasNext()){
                //调用next方法取出某个就绪状态（连接内容）
                SelectionKey selectionKey = iterator.next();

                // 6. 根据就绪状态，调用对应方法实现具体业务操作
                //6.1 accept状态
                if(selectionKey.isAcceptable()){
                    //自定义方法，对accept状态进行处理
                    acceptOperator(ssc,selector);
                }
                //6.2 可读状态
                if(selectionKey.isReadable()){
                    //自定义方法，对可读状态进行处理
                    readOperator(selector,selectionKey);
                }

                // 7. 取出的selectionKey，已经处理完了，不能再取第二次了，所以要从集合中移除掉
                iterator.remove();

            }

        }

    }


    /**
     * 对 accept 就绪状态进行处理：将接入的客户端Channel绑定到selector
     * @param ssc 用于将客户端Channel接入，获取ServerSocket
     * @param selector 获取的channel要绑定到该selector
     */
    public void acceptOperator(ServerSocketChannel ssc,Selector selector) throws IOException {
        // 1. 调用accept，获取SocketChannel，并设置为非阻塞模式
        SocketChannel channel = ssc.accept();
        channel.configureBlocking(false);

        // 2. 把接入的channel注册到selector上，监听可读状态
        channel.register(selector,SelectionKey.OP_READ);

        // 3. 向客户端回复一条信息
        channel.write(Charset.forName("UTF-8").encode("欢迎进入聊天室"));
    }

    /**
     * 对 selector中的 可读状态进行处理：获取读到的信息
     * @param selector
     * @param selectionKey
     */
    private void readOperator(Selector selector, SelectionKey selectionKey) throws IOException {
        //1. 从SelectionKey中获取已经就绪的通道
        SocketChannel channel = (SocketChannel) selectionKey.channel();

        //2. 创建buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        //3. 循环读取客户端发送的消息
        int count = channel.read(buffer);
        String message = "";
        if(count>0){
            buffer.flip();
            message += Charset.forName("UTF-8").decode(buffer);
        }

        //4. 数据读完后，将该 channel 再次注册到 selector 中，监听可读状态
        channel.register(selector,SelectionKey.OP_READ);

        //5. 把客户端发送的消息，广播到其他客户端（群聊）
        if(message.length()>0){
            // 自定义一个方法，进行广播操作（无需给自己再广播）
            System.out.println(message);
            castOtherClient(message,selector,channel);
        }

    }

    /**
     * 服务端接收到客户端发送的消息后，广播到其他客户端
     * @param message 客户端发送的消息
     * @param selector 通过selector获取其他客户端的channel
     * @param sourChannel 发送消息的客户端，广播的时候排除它
     */
    private void castOtherClient(String message, Selector selector, SocketChannel sourChannel) throws IOException {
        //1. 获取所有已经接入的客户端
        Set<SelectionKey> allSelectionKey = selector.keys();


        //2. 循环向所有的channel中广播消息
        for(SelectionKey selectionKey : allSelectionKey){
            //获取所有的channel
            Channel targetChannel = selectionKey.channel();
            //发送消息前排除自己
            if(targetChannel instanceof SocketChannel && targetChannel != sourChannel){
                ((SocketChannel)targetChannel).write(Charset.forName("UTF-8").encode(message));
            }
        }
    }
}





























