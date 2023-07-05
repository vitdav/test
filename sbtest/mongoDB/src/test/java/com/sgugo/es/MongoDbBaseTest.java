package com.sgugo.es;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class MongoDbBaseTest {
    @Resource
    MongoTemplate mongoTemplate;

    @Test
    void insertTest(){
        Student student = new Student();
        student.setUserid(10);
        student.setUsername("Aaron");
        student.setAge(20);
        student.setGender("male");
        mongoTemplate.insert(student);
    }

    @Test
    void remoteTest(){
        Query query = new Query(Criteria.where("username").is(null));
        DeleteResult result = mongoTemplate.remove(query,Student.class);
        System.out.println(result.getDeletedCount());
    }

    @Test
    void updateTest(){
        Query query = new Query(Criteria.where("age").is(20));
        Update update = new Update().set("age", 10);
        UpdateResult updateResult = mongoTemplate.updateMulti(query, update, Student.class);
        System.out.println(updateResult.getModifiedCount());

    }

    @Test
    void findAllTest(){
        List<Student> all = mongoTemplate.findAll(Student.class);
        System.out.println(all);
    }

    @Test
    void findByItTest(){
        Student result = mongoTemplate.findById("64a4d9aaad5837556a80e329", Student.class);
        System.out.println(result);
    }

    @Test
    void findTest(){
        Query query = new Query(Criteria.where("username").is("Jinno").and("age").gt(5));
        List<Student> students = mongoTemplate.find(query, Student.class);
        System.out.println(students);
    }
}
