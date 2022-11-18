package com.in28minutes.springboot.firstrestapi.utils;

import java.util.Base64;

import org.springframework.stereotype.Component;

@Component
public class SecurityUtility {
    public String performBasicAuthEncoding(String user, String password){
        String combined = user + ":" + password;
        byte[] encodedBytes = Base64.getEncoder().encode(combined.getBytes());
        return new String(encodedBytes);
    }
}
