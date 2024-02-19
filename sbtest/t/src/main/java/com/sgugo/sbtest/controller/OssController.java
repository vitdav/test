package com.sgugo.sbtest.controller;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.model.*;
import com.sgugo.sbtest.properties.AliOssProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.*;
import java.util.ArrayList;
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

    @GetMapping("/download_file")
    public void downloadFile(){
        //1. 创建OSSClient
        OSS ossClient = OSSInit();

        //2. 指明访问的 bucketName，和 文件名
        String bucketName = aliOssProperties.getBucket();
        String objectName = "f1.jpg";
        File f1 = new File("out.jpg"); //要下载的路径

        //3. 调用getObject方法下载文件
        ossClient.getObject(new GetObjectRequest(bucketName,objectName),f1);

        ossClient.shutdown();
    }

    @GetMapping("/delete_multi")
    public void deleteMulti(){
        // 1. 创建OSSClient，指明访问的 bucketName
        OSS ossClient = OSSInit();
        String bucketName = aliOssProperties.getBucket();

        // 2. 指明要三次的文件名集合（List）
        ArrayList<String> keys = new ArrayList<>();
        keys.add("1.jpg");
        keys.add("2.jpg");
        keys.add("3.jpg");

        // 3.进行删除
        DeleteObjectsResult request = ossClient.deleteObjects(
                new DeleteObjectsRequest(bucketName)
                    .withKeys(keys) //指定要删除的文件名集合
                    .withEncodingType("url") //指定文件名的编码
        );
        // 4. （可选）获取删除的结果：返回所有删除的文件列表
        List<String> deletedObjects = request.getDeletedObjects();

        ossClient.shutdown();
    }

    @GetMapping("/delete_dir")
    public void deleteDir(){
        // 1. 创建OSSClient，指明访问的 bucketName
        OSS ossClient = OSSInit();
        String bucketName = aliOssProperties.getBucket();

        //2. 先列举所有包含指定前缀的文件
        ListObjectsRequest request = new ListObjectsRequest(bucketName);
        // withPrefix：指定前缀，如果是空或NULL，会删除整个Bucket
        request.withPrefix("user/").withMarker(null);

        ObjectListing objectListing = ossClient.listObjects(request);

        // 3.将猎取出来的文件加入List集合
        if(objectListing.getObjectSummaries().size()>0){
            ArrayList<String> keys = new ArrayList<>();
            for(OSSObjectSummary s: objectListing.getObjectSummaries()){
                keys.add(s.getKey());
            }

            // 4. 批量删除List集合中的所有文件
            DeleteObjectsResult deleteObjectsResult = ossClient.deleteObjects(
                    new DeleteObjectsRequest(bucketName)
                            .withKeys(keys) //指定要删除的文件名集合
                            .withEncodingType("url") //指定文件名的编码
            );

            //5. （可选）获取删除的结果：返回所有删除的文件列表
            List<String> deletedObjects = deleteObjectsResult.getDeletedObjects();

            ossClient.shutdown();
        }


    }

    @GetMapping("/is_have")
    public void isHave(){
        // 1. 创建OSSClient
        OSS ossClient = OSSInit();

        //2. 指明访问的 bucketName，和 文件名
        String bucketName = aliOssProperties.getBucket();
        String objectName = "f2.jpg";

        //3. 调用方法判断文件是否存在：true为存在，false为不存在
        boolean res = ossClient.doesObjectExist(bucketName, objectName);
        System.out.println(res);
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
