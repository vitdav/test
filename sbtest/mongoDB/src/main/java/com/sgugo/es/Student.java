package com.sgugo.es;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;

/**
 * @Author: Aaron Jinno
 * @Description: TODO
 * @Date: 2023/7/4 21:00
 * @Since 1.0
 * @Version: 1.0
 */
@Data
@Document(collection="student")
public class Student {
    @Id
    private String id;

    private long userid;
    private String username;
    private int age;
    private String gender;
}

