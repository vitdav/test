package com.sgugo.sbtest.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * 客户端处理接收消息的子线程类
 */
public class ClientThread implements Runnable{
    private Selector selector;
    public ClientThread(Selector selector){
        this.selector = selector;
    }

    @Override
    public void run() {
        // 循环，等待是否有新的连接接入
        try{
            for(;;){
                // 调用select，获取就绪的channel数量
                int num = selector.select();
                //未获取就直接跳出本次循环
                if(num == 0){
                    continue;
                }
                // 获取 SelectedKeys的集合，保护所有就绪的状态
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();

                // 5. 迭代SelectionKey，对每个值进行判断并做处理
                while(iterator.hasNext()){
                    //调用next方法取出某个就绪状态（连接内容）
                    SelectionKey selectionKey = iterator.next();

                    // 监听可读状态
                    if(selectionKey.isReadable()){
                        //自定义方法，对可读状态进行处理
                        readOperator(selector,selectionKey);
                    }

                    // 7. 取出的selectionKey，已经处理完了，不能再取第二次了，所以要从集合中移除掉
                    iterator.remove();

                }

            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

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
            System.out.println(message);
        }

    }
}
