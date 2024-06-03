package com.example.api.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.api.dto.UsuarioDTO;
import com.example.api.exception.ErrorRuntimeException;
import com.example.api.models.Usuario;
import com.example.api.repository.UsuarioRepository;
import com.example.api.request.CadastroUsuario;
import com.example.api.security.GetUserFromJwt;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final GetUserFromJwt userFromJwt;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, GetUserFromJwt userFromJwt){
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.userFromJwt = userFromJwt;
    }

    public UsuarioDTO findByEmail(String email) throws UsernameNotFoundException{
        return Optional.ofNullable(this.usuarioRepository.findByEmail(email)).map(u -> new UsuarioDTO(u.get())).orElseThrow(() -> new UsernameNotFoundException("usuário não encontrado"));
    }

    public UsuarioDTO save(CadastroUsuario cadastroUsuario) throws ErrorRuntimeException{
        this.usuarioRepository.findByEmail(cadastroUsuario.email()).ifPresent(u -> {
            throw new ErrorRuntimeException("email inválido");
        });
        Usuario u = new Usuario();
        u.setNome(cadastroUsuario.nome());
        u.setEmail(cadastroUsuario.email());
        u.setSenha(passwordEncoder.encode(cadastroUsuario.senha()));
        u = this.usuarioRepository.save(u);
        return new UsuarioDTO(u);
    }

    public UsuarioDTO update(CadastroUsuario cadastroUsuario, HttpServletRequest request){
        final Usuario usuario = this.userFromJwt.load(request);
        if(!cadastroUsuario.email().equals(usuario.getEmail())){
            this.usuarioRepository.findByEmail(cadastroUsuario.email()).ifPresent(u -> {
                throw new ErrorRuntimeException("email inválido");
            });
        }
        usuario.setNome(cadastroUsuario.nome());
        usuario.setEmail(cadastroUsuario.email());
        usuario.setSenha(passwordEncoder.encode(cadastroUsuario.senha()));
        this.usuarioRepository.save(usuario);
        return new UsuarioDTO(usuario);
    }

    public void delete(HttpServletRequest request){
        final Usuario usuario = this.userFromJwt.load(request);
        usuario.setAtivo(false);
        this.usuarioRepository.save(usuario);
    }
}
