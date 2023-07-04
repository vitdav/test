package com.sgugo.es;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Aaron Jinno
 * @Description: TODO
 * @Date: 2023/7/4 21:57
 * @Since 1.0
 * @Version: 1.0
 */
@SpringBootTest
public class EsTests {
    @Resource
    private MongoTemplate mongoTemplate;

    @Test
    void findAll(){
        System.out.println(mongoTemplate);

        List<Student> all = mongoTemplate.findAll(Student.class);
        System.out.println(all);
    }
}

