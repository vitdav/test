package com.sgugo.sky.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;

@Data
@AllArgsConstructor
@Slf4j
public class AliOssUtil {
    //这些参数不用赋值，在配置类中进行初始化
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;

    /**
     * 文件上传
     * @param bytes 要上传的数据，这里采用byte[]格式
     * @param objectName 文件的名字
     * @return 文件在阿里云的访问链接
     */
    public String upload(byte[] bytes,String objectName){

        //1. 创建 OSS 实例
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        //2. 调用putObject 方法，上传文件

        try{
            //创建文件流
            ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
            ossClient.putObject(bucketName,objectName,stream);
        }catch(OSSException e){
            System.out.println("OSSException：请求已发送到OSS，但被意外拒绝了");
            System.out.println("错误信息：" + e.getMessage());
            System.out.println("本次请求的UUID：" + e.getErrorCode());
            System.out.println("访问的OSS集群 host：" + e.getHostId());
        }catch(ClientException e){
            System.out.println("ClientException：连接OSS错误");
            System.out.println("错误信息：" + e.getMessage());
        }finally{
            //3. 关闭ossClient
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }

        // 4. 拼接上传后，文件的访问路径
        // 拼接结果：https://BucketName.Endpoint/ObjectName
        StringBuilder stringBuilder = new StringBuilder("https://");
        stringBuilder
                .append(bucketName)
                .append(".")
                .append(endpoint)
                .append("/")
                .append(objectName);


        return stringBuilder.toString();
    }
}
