package com.sgugo.sky.config;

import com.sgugo.sky.properties.AliOssProperties;
import com.sgugo.sky.utils.AliOssUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类，用于创建AliOssUtil对象
 */
@Configuration

public class OssConfiguration {
    @Bean
    @ConditionalOnMissingBean //没有该Bean的时候再创建
    public AliOssUtil aliOssUtil(AliOssProperties aliOssProperties){
        return new AliOssUtil(
                aliOssProperties.getEndpoint(),
                aliOssProperties.getAccessKeyId(),
                aliOssProperties.getAccessKeySecret(),
                aliOssProperties.getBucketName()
        );
    }
}
