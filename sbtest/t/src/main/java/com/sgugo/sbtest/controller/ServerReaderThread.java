package com.sgugo.sbtest.controller;


import java.io.*;
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
                    // 把收到的消息分发给全部客户端进行接收
                    sendMsgToAll(socket.getRemoteSocketAddress()+"："+msg);
                } catch (IOException e) {
                    System.out.println(socket.getRemoteSocketAddress()+"-已经下线了");
                    // 该线程对接的客户端离线了，将该Socket从集合中剔除
                    Server.onLineSockets.remove(socket);
                    dis.close();
                    socket.close();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将该线程对接的客户端发送的消息转发给所有在线的客户端Socket
     */
    private void sendMsgToAll(String msg) throws IOException {
        // 遍历OnLineSocket集合，获取所有在线的Socket通信管道
        for(Socket onLineSocket : Server.onLineSockets){
            // 从在线的Socket上获取输出流，以便将消息通过流发送过去
            OutputStream os = onLineSocket.getOutputStream();
            DataOutputStream dos = new DataOutputStream(os);
            // 将消息通过输出流发送出去
            dos.writeUTF(msg);
            dos.flush(); //刷新一下更保险
        }
    }


}
