package com.example.api.security;

import java.util.logging.Logger;
import java.util.logging.Level;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.api.models.Usuario;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TokenFilter extends OncePerRequestFilter{
    private static final Logger logger = Logger.getLogger(TokenFilter.class.getName());

    @Autowired
    private GetUserFromJwt userFromJwt;

    @Autowired
    private AuthService authService;

    //cada request passa pelo filtro e verifica se o token está no cookie enviado na request, se tiver valida com os métodos da biblioteca e busca o email do usuario de dentro
    //do token pra validar, depois busca no bd o usuario e faz uma tentativa de login, caso a tentativa der uma exceção entao houve algum erro
    //e o spring lança uma exceção
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = GetTokenRequest.getToken(request);

            if (token != null) {
                Usuario usuario = this.userFromJwt.load(token);
                UserDetails userDetails = this.authService.loadUserByUsername(usuario.getEmail());

                //pra autenticar o usuário precisa ter uma instância de userDetails que é uma classe do spring
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,userDetails.getPassword(),userDetails.getAuthorities());
                
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE,e.getMessage(), e);
        }

        filterChain.doFilter(request, response);
    }    
}
