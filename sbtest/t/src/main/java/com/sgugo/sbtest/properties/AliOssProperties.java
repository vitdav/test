package com.sgugo.sbtest.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix="t.alioss")
public class AliOssProperties {

    private String endpoint;
    private String accessKey;
    private String accessSecret;
    private String bucket;
}
