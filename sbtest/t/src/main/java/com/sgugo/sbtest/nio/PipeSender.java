package com.sgugo.sbtest.nio;

import java.io.IOException;
import java.io.PipedOutputStream;

public class PipeSender implements Runnable {
    //定义PipedOutputStream，并使用构造方法赋值
    PipedOutputStream out;
    public PipeSender(PipedOutputStream out){
        super();
        this.out = out;
    }

    @Override
    public void run() {
        // 模拟发送数据
        String text = "Man，what can I say, Mamba out";
        try {
            out.write(text.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
