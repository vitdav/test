package com.sgugo.cache.service.impl;

import com.sgugo.cache.dto.SMSCode;
import com.sgugo.cache.service.SMSCodeService;
import com.sgugo.cache.utils.CodeUtils;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
public class SMSCodeServiceImpl implements SMSCodeService {

    @Resource
    private CodeUtils codeUtils;

    @Override
    @CachePut(value="smsCode",key="#tele")
    public String sendCodeToSMS(String tele) {
        return codeUtils.generator(tele);
    }

    @Override
    public boolean checkCode(SMSCode smsCode) {
        String  code = smsCode.getCode();
        String cacheCode = codeUtils.get(smsCode.getTele());
        return code.equals(cacheCode);
    }
}
