package com.sgugo.cache;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @Author: Aaron Jinno
 * @Description: TODO
 * @Date: 2023/7/6 23:56
 * @Since 1.0
 * @Version: 1.0
 */
public class TaskTest {
    public static void main(String[] args) {
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
