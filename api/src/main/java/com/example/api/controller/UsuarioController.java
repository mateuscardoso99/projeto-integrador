package com.example.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.dto.TokenDTO;
import com.example.api.dto.UsuarioDTO;
import com.example.api.jwt.Jwt;
import com.example.api.request.CadastroUsuario;
import com.example.api.service.UsuarioService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "usuario")
public class UsuarioController {
    private final AuthenticationManager authenticationManager;
    private final UsuarioService usuarioService;
    private final Jwt jwtService;

    public UsuarioController(AuthenticationManager authenticationManager, UsuarioService usuarioService, Jwt jwtService){
        this.usuarioService = usuarioService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody CadastroUsuario dados) {
        Authentication auth = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dados.email(), dados.senha()));

        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        UsuarioDTO usuarioDTO = this.usuarioService.findByEmail(userDetails.getUsername());

        String jwt = this.jwtService.generateJwt(userDetails);;

        return new ResponseEntity<>(new TokenDTO(jwt, usuarioDTO), HttpStatus.OK);
    }
    

    @PostMapping(value = "/cadastro")
    public ResponseEntity<UsuarioDTO> cadastro(@RequestBody @Valid CadastroUsuario usuario){
        UsuarioDTO u = this.usuarioService.save(usuario);
        return new ResponseEntity<>(u, HttpStatus.CREATED);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<UsuarioDTO> update(@RequestBody @Valid CadastroUsuario usuario, HttpServletRequest request){
        UsuarioDTO u = this.usuarioService.update(usuario, request);
        return new ResponseEntity<>(u, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> delete(HttpServletRequest request){
        this.usuarioService.delete(request);
        return new ResponseEntity<>("apagado com sucesso", HttpStatus.OK);
    }
}
