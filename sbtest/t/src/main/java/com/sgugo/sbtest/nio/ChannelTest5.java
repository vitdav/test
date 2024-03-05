package com.sgugo.sbtest.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class ChannelTest5 {
    public static void main(String[] args) throws IOException {
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 1234);

        SocketChannel channel = SocketChannel.open(address);

        System.out.println(channel.isOpen());
        System.out.println(channel.isConnectionPending());
        System.out.println(channel.isConnected());
        System.out.println(channel.isConnectionPending());
        System.out.println(channel.finishConnect());



    }
}
