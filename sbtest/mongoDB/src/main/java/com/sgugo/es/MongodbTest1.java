package com.sgugo.es;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

public class MongodbTest1 {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Test
    public void contextLoads(){
        System.out.println(mongoTemplate);
    }
}
