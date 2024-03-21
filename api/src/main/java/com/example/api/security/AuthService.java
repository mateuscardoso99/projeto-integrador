package com.example.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.api.models.Usuario;
import com.example.api.repository.UsuarioRepository;

@Service
public class AuthService implements UserDetailsService{
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario findUsuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));
        return User.withUsername(findUsuario.getEmail())
                    .password(findUsuario.getSenha())
                    .authorities(findUsuario.getAdmin() ? "ADMIN" : "")
                    .build();
    }
}
