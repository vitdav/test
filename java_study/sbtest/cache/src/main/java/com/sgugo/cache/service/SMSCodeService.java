package com.sgugo.cache.service;

import com.sgugo.cache.dto.SMSCode;

public interface SMSCodeService {
    //根据手机号返回验证码
    public String  sendCodeToSMS(String tele);
    public boolean checkCode(SMSCode smsCode);
}
