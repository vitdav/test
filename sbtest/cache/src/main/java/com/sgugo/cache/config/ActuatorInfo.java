package com.sgugo.cache.config;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ActuatorInfo implements InfoContributor {
    @Override
    public void contribute(Info.Builder builder) {
        //添加单个信息
        builder.withDetail("runTime",System.currentTimeMillis());

        //添加一组信息
        Map infoMap = new HashMap();
        infoMap.put("buildTime","2006");
        infoMap.put("status","2");
        builder.withDetails(infoMap);
    }
}
