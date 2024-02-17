package com.sgugo.sbtest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/file")
public class FileController {


    @GetMapping("createfile")
    public void createFile(){
        System.out.println(System.getProperty("user.dir"));
        File f1 = new File("D:/item/test/sbtest/hello0");

        if(f1.exists()){
            System.out.println("创建文件失败，文件已存在");
            return;
        }

        try {
            boolean res = f1.createNewFile();
            if(res){
                System.out.println("文件创建成功");
            }
        } catch (IOException e) {
            System.out.println("文件创建失败："+e.getMessage());
        }
    }

    @GetMapping("createdir")
    public void createDir(){
        File d1 = new File("dir/dir1");

        if(d1.exists()){
            System.out.println("该目录已经存在");
            return ;
        }

        boolean res = d1.mkdirs();
        if(res){
            System.out.println("目录创建成功");
        }else{
            System.out.println("目录创建失败，请稍后重试");
        }
    }

    @GetMapping("rename")
    public void rename(){
        File f1 = new File("hello");
        File f2 = new File("hi");

        boolean res = f1.renameTo(f2);
        if(res){
            System.out.println("文件改名成功，改为："+f2);
        }else{
            System.out.println("文件改名失败");
        }
    }

    @GetMapping("delete")
    public void delete(){
        File f1 = new File("hello.txt");

        boolean res = f1.delete(); //删除硬盘上的 hello.txt 文件

        if(res){
            System.out.println("文件删除成功");
        }else{
            System.out.println("文件删除失败");
        }
    }

    @GetMapping("getinfo")
    public void getInfo(){
        File f1 = new File("hi");
        File dir = new File("dir");

        boolean res1 = dir.isDirectory();
        boolean res2 = f1.isFile();
        boolean res3 = f1.exists();
        boolean res4 = f1.canExecute();
        boolean res5 = f1.canRead();
        boolean res6 = f1.canWrite();
        boolean res7 = f1.isHidden();

        System.out.println(res1); //true
        System.out.println(res2); //true
        System.out.println(res3);//true
        System.out.println(res4);//true
        System.out.println(res5);//true
        System.out.println(res6); //true
        System.out.println(res7); //false

    }
}
