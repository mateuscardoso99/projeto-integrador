package com.example.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.api.jwt.Jwt;
import com.example.api.models.Usuario;
import com.example.api.repository.UsuarioRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class GetUserFromJwt {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private Jwt jwtService;

    public Usuario load(HttpServletRequest request) throws UsernameNotFoundException{
        String jwt = GetTokenRequest.getToken(request);
        DecodedJWT decodedJWT = this.jwtService.decodeJwt(jwt);
        return this.usuarioRepository.findByEmail(decodedJWT.getSubject()).orElseThrow(() -> new UsernameNotFoundException("usuário não encontrado no jwt"));
    }
}
