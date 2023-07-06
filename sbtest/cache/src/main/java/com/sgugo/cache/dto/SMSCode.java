package com.sgugo.cache.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SMSCode {
    private String  tele;
    private String  code;
}
