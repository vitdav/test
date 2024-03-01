package com.sgugo.sbtest;


import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ServerReaderThread extends Thread{

    private Socket socket;
    public ServerReaderThread(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run(){
        try {
            InputStream is = socket.getInputStream();
            DataInputStream dis = new DataInputStream(is);
            while(true){
                try {
                    String msg = dis.readUTF();
                    System.out.println(socket.getRemoteSocketAddress()+"："+msg);
                } catch (IOException e) {
                    System.out.println(socket.getRemoteSocketAddress()+"-已经下线了");
                    dis.close();
                    socket.close();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            //客户端对应的线程下线后，该线程的socket也要关闭
        }
    }
}
