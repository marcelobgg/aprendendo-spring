package com.marcelo.aprendendospring.controller;

import com.marcelo.aprendendospring.business.UsuarioService;
import com.marcelo.aprendendospring.controller.dtos.UsuarioDTO;
import com.marcelo.aprendendospring.infrastructure.entity.Usuario;
import com.marcelo.aprendendospring.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario") //URI
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    //Feita a injeção da dependência AuthenticationManager para usar o método authenticate
    //que é um método padrão do Spring Security;
    private final JwtUtil jwtUtil;

    @PostMapping //envio de dados
    public ResponseEntity<Usuario> salvaUsuario(@RequestBody Usuario usuario) {
        return ResponseEntity.ok(usuarioService.salvarUsuario(usuario));
    }

    @PostMapping("/login")
    public String login(@RequestBody UsuarioDTO usuarioDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuarioDTO.getEmail(),
                usuarioDTO.getSenha()));
        return "Bearer " + jwtUtil.generateToken(authentication.getName());
    }
    //Foi usado o conceito de DTO, que basicamente é uma classe intermediaria para transitar,
    //somente alguns dos dados da Entity. Essa pratica é comum para evitar de trafegar dados sensíveis,
    //por exemplo.

    @GetMapping
    public ResponseEntity<Usuario> buscaUsuarioPorEmail(@RequestParam("email") String email) {
        return ResponseEntity.ok(usuarioService.buscarUsuarioporEmail(email));
    }
    //ResponseEntity é para que o retorno seja feito dentro dos padrões HTTP, com as especificações corretas.

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deletaUsuarioPorEmail(@PathVariable String email) {
        usuarioService.deletaUsuarioPorEmail(email);
        return ResponseEntity.ok().build();
    }

}
