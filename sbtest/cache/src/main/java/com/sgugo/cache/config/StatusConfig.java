package com.sgugo.cache.config;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

/**
 * @Author: Aaron Jinno
 * @Description: TODO
 * @Date: 2023/7/7 21:04
 * @Since 1.0
 * @Version: 1.0
 */
@Component
public class StatusConfig extends AbstractHealthIndicator {

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        boolean status = true;
        if(status){
            builder.status(Status.UP);
            builder.withDetail("什么情况","程序在正常运行");
        }else{
            builder.status(Status.DOWN);
            builder.withDetail("什么情况","太逊了，程序已经掉线");
        }
    }
}
