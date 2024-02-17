package com.sgugo.sbtest.controller;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.sgugo.sbtest.properties.AliOssProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.File;

@RestController()
@RequestMapping("t")
public class OssController {

    // 注入OSS配置文件
    @Resource
    private AliOssProperties aliOssProperties;

    // 简单文件上传
    @GetMapping("/upload")
    public void upload(){
        // 1. 引入阿里云OSS配置项：endpoint、bucket 和访问凭证
        String endpoint = aliOssProperties.getEndpoint();
        String bucket = aliOssProperties.getBucket();
        String accessKey = aliOssProperties.getAccessKey();
        String accessSecret = aliOssProperties.getAccessSecret();

        // 2. 创建 File 对象
        File f1 = new File("001.jpg");

        DefaultCredentialProvider provider = CredentialsProviderFactory.newDefaultCredentialProvider(accessKey, accessSecret);

        // 3. 创建OSSClient配置
        ClientBuilderConfiguration conf = new ClientBuilderConfiguration();

        
        // 3. 创建OSS实例
        OSS ossClient = new OSSClientBuilder().build(endpoint, provider,conf);


        ossClient.shutdown();

    }

    @GetMapping("/download")
    public void download(){

    }

    @GetMapping("/delete")
    public void delete(){

    }


    @GetMapping("/list")
    public void list(){

    }

    @GetMapping("/addUpload")
    public void addUpload(){

    }

    @GetMapping("/partUpload")
    public void partUpload(){

    }

}
