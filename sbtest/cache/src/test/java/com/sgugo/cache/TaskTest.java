package com.sgugo.cache;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @Author: Aaron Jinno
 * @Description: TODO
 * @Date: 2023/7/6 23:50
 * @Since 1.0
 * @Version: 1.0
 */
@SpringBootTest
public class TaskTest {

    @Test
    void timerTest(){
        Timer timer = new Timer();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("每秒一次：酷");
            }
        };

        timer.schedule(task,0,2000);
    }
}
