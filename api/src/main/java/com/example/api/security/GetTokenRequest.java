package com.example.api.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

public class GetTokenRequest {
    //como a autenticação é feita por cookie que é enviado em todas as requests pelo navegador
    //não se usa o header Authorization

    /*public static String getToken(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        if(header != null){
            header = header.split(" ")[1].trim();
        }
        return header;
    }*/


    /**
     * Pela própria forma padrão como browsers funcionam, temos que eles enviam de volta para o servidor todos os cookies setados por esse mesmo 
     * domínio a cada requisição, ou seja, em todas as chamadas subsequentes a essa para nosso backend iríamos ter um cookie com o nosso token de 
     * usuário por lá
     * @param request
     * @return
     */
    public static String getToken(HttpServletRequest request){
        String token = null;
        if(request.getCookies() != null){
            for(Cookie cookie : request.getCookies()){
                if(cookie.getName().equals("jwt")){
                    token = cookie.getValue();
                }
            }
        }
        return token;
    }
}
