package com.sbtest.security.controller;

import com.google.code.kaptcha.Producer;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
public class KaptchaController {

    //注入配置类中创建的默认验证码实例
    @Autowired
    private Producer producer;

    //前后端不分离：设置一个接口返回验证码图片
    @GetMapping("/vc.jpg")
    public void getKaptcha(HttpServletResponse response, HttpSession session) throws IOException{
        //1. 生成验证码文本：调用Producer.createText()
        String code = producer.createText();
        //2. 将生成的验证码保存到Session：以便后续验证
        session.setAttribute("kaptcha",code);
        //3. 根据验证码文本生成验证码图片：调用Producer.createImage()
        BufferedImage image = producer.createImage(code);
        //4. 响应图片，需要先设置好响应类型
        response.setContentType("image/png");
        ServletOutputStream os = response.getOutputStream();
        ImageIO.write(image,"jpg",os);
    }

    //前后端分离版
    @GetMapping("/vc")
    public String getKaptcha(HttpSession session) throws IOException {
        //1.生成验证码文本：调用Producer.createText()
        String code = producer.createText();
        //2.储存验证码：将生成的验证码保存到存入Session或Redis
        session.setAttribute("kaptcha",code);
        //3. 根据验证码文本生成验证码图片：调用Producer.createImage()
        BufferedImage image = producer.createImage(code);
        //4. 将生成的图片转为字节流
        FastByteArrayOutputStream fs = new FastByteArrayOutputStream();
        ImageIO.write(image,"jpg",fs);
        //5.返回base64
        return Base64.encodeBase64String(fs.toByteArray());
    }
}
