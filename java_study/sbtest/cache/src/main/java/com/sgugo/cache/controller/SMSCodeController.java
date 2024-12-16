package com.sgugo.cache.controller;

import com.sgugo.cache.dto.SMSCode;
import com.sgugo.cache.service.SMSCodeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/sms")
public class SMSCodeController {
    @Resource
    private SMSCodeService smsCodeService;

    @GetMapping
    public String getCode(String tele){
        return smsCodeService.sendCodeToSMS(tele);
    }


    @PostMapping
    public boolean checkCode(SMSCode smsCode){
        return smsCodeService.checkCode(smsCode);
    }
}
