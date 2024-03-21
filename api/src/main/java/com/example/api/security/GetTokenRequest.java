package com.example.api.security;

import jakarta.servlet.http.HttpServletRequest;

public class GetTokenRequest {
    public static String getToken(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        if(header != null){
            header = header.split(" ")[1].trim();
        }
        return header;
    }
}
