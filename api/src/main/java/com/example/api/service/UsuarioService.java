package com.example.api.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.api.dto.UsuarioDTO;
import com.example.api.models.Usuario;
import com.example.api.repository.UsuarioRepository;
import com.example.api.request.CadastroUsuario;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder){
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UsuarioDTO save(CadastroUsuario cadastroUsuario){
        Usuario u = new Usuario();
        u.setEmail(cadastroUsuario.email());
        u.setSenha(passwordEncoder.encode(cadastroUsuario.senha()));
        u = this.usuarioRepository.save(u);
        return UsuarioDTO.convert(u, null);
    }

    public void delete(){
        Usuario usuario = null;
        this.usuarioRepository.delete(usuario);
    }
}
