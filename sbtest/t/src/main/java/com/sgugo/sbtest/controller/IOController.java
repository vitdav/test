package com.sgugo.sbtest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.Arrays;

@RestController
@RequestMapping("io")
public class IOController {

    @GetMapping("read1")
    public void read1(){
        //1. 初始化FileReader
        File f1 = new File("hi");
        FileReader fr = null;
        try { //初始化要处理FileNotFoundException异常，read方法要处理IO异常
            fr = new FileReader(f1); //初始化
            int data = fr.read(); //读取一个字符，指针会自动后移，返回ASCII码，-1表示文件读完了
            while(data != -1){ //循环读取所有字符，一次读一个
                System.out.println((char)data);
                data = fr.read();
            }
        } catch (IOException e) {
            System.out.println("文件读取异常："+e.getMessage());
        } finally{
            try {
                if(fr != null){
                    fr.close();
                }
            } catch (IOException e) { //关闭流也会报IO错误
                System.out.println("流关闭错误："+e.getMessage());
            }
        }
    }

    @GetMapping("read2")
    public void read2(){
        FileReader fr = null;
        try {
            fr = new FileReader("hi");
            char[] cbuf = new char[4];
            int len = fr.read(cbuf);//返回读取的字节数，-1表示为读取字符
            //此时 buff 数组被赋值了，可以直接读buff数组，获取具体的字符数据
            while(len != -1){
                //循环cbuf数组，获取里面具体的字符数据
                for(int i=0;i<len;i++){ //len同时也是cbuf数组里有效字符的长度
                    System.out.println(cbuf[i]);
                }
                len = fr.read(cbuf);
            }
        } catch (IOException e) {
            System.out.println("文件读取错误"+e.getMessage());
        }finally {
            try {
                if(fr != null)
                    fr.close();
            } catch (IOException e) {
                System.out.println("文件流关闭失败："+e.getMessage());
            }
        }
    }

    @GetMapping("write1")
    public void write1(){
        File f1 = new File("hi");

        FileWriter fw = null;
        try {
            fw = new FileWriter(f1, false);
            fw.write("do you like van youxi \n");
            fw.write("are you ok");
        } catch (IOException e) {
            System.out.println("文件打开失败："+e.getMessage());
        } finally {
            try {
                //流资源的关闭,开启成功了才需要关闭
                if (fw != null)
                    fw.close();
            } catch (Exception e) {
                System.out.println("流关闭失败："+e.getMessage());
            }
        }

    }

    @GetMapping("copy")
    public void read3(){
        File f1 = new File("001.jpg");
        long length = f1.length();

        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            // 初始化，参数既可以是File对象，也可以是文件路径
            fis = new FileInputStream(f1);
            fos = new FileOutputStream("002.jpg");
            // 和 字符流一样，可以将文件读入到 byte[]
            byte[] buffer = new byte[(int)length];
            fis.read(buffer); //文件已经被读入到了buffer
            System.out.println(Arrays.toString(buffer));
            //将字节数据写出到文件，参数也可以是byte[]
            fos.write(buffer);
        } catch (IOException e) {
            System.out.println("文件读取错误："+e.getMessage());
        } finally {
            try {
                if(fis != null){
                    fis.close();
                }
                if(fos != null){
                    fos.close();
                }
            } catch (IOException e) {
                System.out.println("流关闭失败："+e.getMessage());
            }
        }
    }

    @GetMapping("buffer")
    public void buffer(){
        BufferedReader br = null;
        BufferedWriter bw = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        try {
            //1. 先创建节点流，需要什么类型就创建什么类型
            FileReader fr = new FileReader("hi");
            FileWriter fw = new FileWriter("hello");
            FileInputStream fis = new FileInputStream("001.jpg");
            FileOutputStream fos = new FileOutputStream("002.jpg");


            // 2. 根据节点流创建缓冲流
            br = new BufferedReader(fr);
            bw = new BufferedWriter(fw);
            bis = new BufferedInputStream(fis);
            bos = new BufferedOutputStream(fos);

            // 3. copy：进行字符缓冲流的读写
            char[] cbuf = new char[10];
            int len1 = br.read(cbuf);
            while(len1 != -1){
                bw.write(cbuf,0,len1);
                len1 = br.read(cbuf);
            }


            // 4. copy：进行字节缓冲流的读写
            byte[] buf = new byte[1024];
            int len2 = bis.read(buf);
            while(len2 != -1){
                bos.write(buf,0,len2);
                len2 = bis.read(buf);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally{
            try {
                // 5. 关闭流：只需要关闭缓冲流，节点流会自动关闭
                assert bis != null;
                bis.close();
                assert bos != null;
                bos.close();
                br.close();
                bw.close();
            } catch (IOException e) {
                System.out.println("流关闭失败："+e.getMessage());
            }
        }
    }

    @GetMapping("read4")
    public void read4(){
        BufferedReader br = null;
        try {
            FileReader fr = new FileReader("hi");
            br = new BufferedReader(fr);
            String data = br.readLine();
            while(data != null){
                System.out.println(data);
                data = br.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                assert br != null;
                br.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
