package com.sgugo.sbtest.jvm;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.ArrayList;

@RestController
public class Optimize implements Serializable {

    @GetMapping("/optimize/cpu")
    public void CpuTest(){
        while(true){
            System.out.println(Thread.currentThread().getName());
        }
    }

    @GetMapping("/slow")
    public void a(int i) throws InterruptedException {
        if(i <= 0){
            return;
        }
        Thread.sleep(1000);
        b();
    }

    public void b() throws InterruptedException {
        Thread.sleep(1000);
    }

    @GetMapping("/profile2")
    public void test7(){
        ArrayList<Object> objects = new ArrayList<>(20000000);
        for(Integer i = 0; i < 20000000; i++){
            objects.add(i);
        }
    }

    private Object obj1 = new Object();
    private Object obj2 = new Object();

    @GetMapping("/deadlock1")
    public String test2() throws InterruptedException {
        synchronized(obj1){
            Thread.sleep(5000);
            synchronized(obj2){
                return "返回成功";
            }
        }
    }

    @GetMapping("/deadlock2")
    public String test3() throws InterruptedException {
        synchronized(obj2){
            Thread.sleep(5000);
            synchronized(obj1){
                return "返回成功";
            }
        }
    }

}
