package com.example.api.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.dto.UsuarioDTO;
import com.example.api.jwt.Jwt;
import com.example.api.request.CadastroUsuario;
import com.example.api.service.UsuarioService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
    public ResponseEntity<UsuarioDTO> login(@RequestBody CadastroUsuario dados, HttpServletResponse response) {
        Authentication auth = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dados.email(), dados.senha()));

        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        UsuarioDTO usuarioDTO = this.usuarioService.findByEmail(userDetails.getUsername());

        String jwt = this.jwtService.generateJwt(userDetails);

        ResponseCookie cookie = ResponseCookie.from("jwt", jwt)
            .httpOnly(true) //Cookies com essa propriedade não são acessíveis pelo JavaScript, apenas pelo browser e pelas requisições. impossiblitando XSS
            .secure(false) //somente pode ser usado com conexões HTTPS
            .path("/")
            .sameSite("strict") // SameSite impede que o navegador envie esse cookie junto com solicitações entre sites. O principal objetivo é mitigar o risco de vazamento de informações entre origens. Ele também fornece alguma proteção contra ataques de falsificação de solicitação entre sites. "strict": impedirá que o cookie seja enviado pelo navegador ao site de destino em todos os contextos de navegação entre sites, mesmo ao seguir um link normal. Por exemplo, para um site semelhante ao GitHub, isso significaria que se um usuário conectado seguir um link para um projeto privado do GitHub postado em um fórum de discussão corporativo ou e-mail, o GitHub não receberá o cookie de sessão e o usuário não poderá para acessar o projeto. "lax": fornece um equilíbrio razoável entre segurança e usabilidade para sites que desejam manter a sessão de login do usuário após ele chegar de um link externo. "none": valor não dará nenhum tipo de proteção. O navegador anexa os cookies em todos os contextos de navegação entre sites.
            .maxAge(28800)
            .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return new ResponseEntity<>(usuarioDTO, HttpStatus.OK);
    }
    

    @PostMapping(value = "/cadastro")
    public ResponseEntity<UsuarioDTO> cadastro(@RequestBody @Valid CadastroUsuario usuario){
        UsuarioDTO u = this.usuarioService.save(usuario);
        return new ResponseEntity<>(u, new HttpHeaders(), HttpStatus.CREATED);
    }

    @GetMapping(value = "/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession(false);

        SecurityContextHolder.clearContext();

        if (session != null) {
            session.invalidate();
        }

        //apaga o cookie com o jwt
        for (Cookie cookie : request.getCookies()) {
            cookie.setMaxAge(0);
            cookie.setValue("");
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        return new ResponseEntity<>(HttpStatus.OK);
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
