package com.sgugo.es;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Aaron Jinno
 * @Description: TODO
 * @Date: 2023/7/6 0:16
 * @Since 1.0
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String username;
    private int age;
    private String intro;
}


