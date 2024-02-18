package com.sgugo.sbtest.controller;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.PutObjectResult;
import com.sgugo.sbtest.properties.AliOssProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.*;
import java.util.List;

@RestController()
@RequestMapping("t")
public class OssController {

    // 注入OSS配置文件
    @Resource
    private AliOssProperties aliOssProperties;


    public OSS OSSInit(){
        // 1. 引入阿里云OSS配置项：endpoint、和访问凭证
        String endpoint = aliOssProperties.getEndpoint();
        String accessKey = aliOssProperties.getAccessKey();
        String accessSecret = aliOssProperties.getAccessSecret();

        //3. 配置环境变量
        DefaultCredentialProvider provider = CredentialsProviderFactory.newDefaultCredentialProvider(accessKey, accessSecret);

        //4.创建OSS实例
        return new OSSClientBuilder().build(endpoint, provider);
    }


    // 简单文件上传
    @GetMapping("/upload")
    public void upload(){
        //1. 创建OSSClient，定义bucketName
        OSS ossClient = OSSInit();
        String bucketName = aliOssProperties.getBucket();
        //2. 定义文件上传后的名字，名字可以带 `/` （Object 完整路径）
        String objectName = "t1.jpg";

        //3. 创建文件上传的流：这里是图片，因此采用字节读入流（或对应的缓冲流）
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("001.jpg");

            //4. 调用putObject进行文件上传
            ossClient.putObject(bucketName,objectName,fis);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            //5. 关闭OSS
            ossClient.shutdown();
            try {
                assert fis != null;
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @GetMapping("upload_file")
    public void uploadFile(){
        //1. 创建OSSClient
        OSS ossClient = OSSInit();

        // 2. 定义bucketName 和 上传后的文件名
        String bucketName = aliOssProperties.getBucket();
        String objectName = "f1.jpg";

        //3. 进行上传
        PutObjectResult result = ossClient.putObject(bucketName, objectName, new File("001.jpg"));

        ossClient.shutdown();
    }

    @GetMapping("/download")
    public void download(){
        //1. 创建OSSClient
        OSS ossClient = OSSInit();

        //2. 指明访问的 bucketName，和 文件名
        String bucketName = aliOssProperties.getBucket();
        String objectName = "t1.jpg";

        //3. 调用getObject 访问OSS里的文件
        OSSObject ossObject = ossClient.getObject(bucketName, objectName);

        //4. 获取文件输入流，可读取此输入流获取其内容
        InputStream content = ossObject.getObjectContent();

        if(content == null){
            return;
        }
        //5. 创建缓冲流，读取输入流传输的内容,
        // 这里在额外创建一个输出流，完成将文件下载到本地
        BufferedInputStream bis = new BufferedInputStream(content);
        BufferedOutputStream bos = null;
        try {
            FileOutputStream fos = new FileOutputStream("out.jpg");
            bos = new BufferedOutputStream(fos);

            byte[] buf = new byte[1024];
            int len = bis.read(buf);
            while(len != -1){
                bos.write(buf,0,len); //将读到的字节流写出
                len = bis.read(buf);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                bis.close();
                assert bos != null;
                bos.close();
                ossClient.shutdown();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @GetMapping("/list")
    public void info(){
        //1. 创建OSS客户端，指定要访问的Bucket
        OSS ossClient = OSSInit();
        String bucketName = aliOssProperties.getBucket();

        //2. 调用listObjects方法获取ObjectListing实例
        ObjectListing objectListing = ossClient.listObjects(bucketName,"user/avatar/admin/");
        //3. 调用ObjectListing.getObjectSummaries获取所有文件的描述信息（list集合）
        List<OSSObjectSummary> list = objectListing.getObjectSummaries();
        //4. 遍历集合中的每一个文件
        for(OSSObjectSummary summary : list){
            System.out.println(
                "-" + summary.getKey() + " " + "(size = " + summary.getSize()+")"
            );
        }

        ossClient.shutdown();
    }


    @GetMapping("/delete")
    public void delete(){
        //1. 创建OSS客户端
        OSS ossClient = OSSInit();

        // 2. 指定要访问的Bucket 和 要三次的文件
        String bucketName = aliOssProperties.getBucket();
        String ObjectName = "t1.jpg";

        //3. 删除文件
        ossClient.deleteObject(bucketName, ObjectName);

        ossClient.shutdown();
    }


    @GetMapping("/addUpload")
    public void addUpload(){

    }

    @GetMapping("/partUpload")
    public void partUpload(){

    }

}
