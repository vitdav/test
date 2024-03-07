package com.sgugo.sbtest.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChannelTest2 {
    public static void main(String[] args) throws Exception {
        // 1. 创建要读取的文件
        File file = new File("files/1.txt");
        //1.1 获取文件的属性，并拼接成字符串
        String path = file.getAbsolutePath(); //路径
        long modified = file.lastModified(); //修改时间
        long length = file.length();//文件大小
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(modified));
        String headerText = "文件名：" + path + "\n"
                + "最后修改时间：" + date + "\n"
                + "文件大小：" + length + "\n";

        // 2.创建缓冲区：储存文件属性和文件类型
        ByteBuffer headerBuffer = ByteBuffer.wrap(headerText.getBytes());
        ByteBuffer typeBuffer = ByteBuffer.allocate(80);
        //2.1 获取文件类型，并写入到typeBuffer
        String type = URLConnection.guessContentTypeFromName(file.getAbsolutePath());
        typeBuffer.put(("文件类型："+type +"\r\n").getBytes());
        typeBuffer.clear(); //注意put完，clear一下，把position调到0，待会还要读它
        //关于文件内容的缓冲区：通过文件映射到虚拟内存中获取

        // 3. 创建一个Channel，用于并将文件映射到虚拟内存中
        FileInputStream fis = new FileInputStream(file);
        FileChannel channel = fis.getChannel();
        //此时获取了文件内容的缓冲区
        MappedByteBuffer FileData = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());

        // 4. 创建缓冲区数组，用来进行Gather
        //文件属性缓冲区、文件类型和长度缓冲区、文件内容缓冲区
        ByteBuffer[] gather = {headerBuffer,typeBuffer,FileData};

        // 5. 把gather数组中的内容写入到目标文件中
        //创建一个流进行gather写入
        FileOutputStream fos = new FileOutputStream("files/gatherOut.txt");
        FileChannel outChannel = fos.getChannel();
        long res = outChannel.write(gather);
        //一次不一定能写完，进行while判断，循环写出
        while(res>0){
            res = outChannel.write(gather);
        }
        outChannel.close();
    }
}
