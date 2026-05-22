package com.marcelo.aprendendospring.business;

import com.marcelo.aprendendospring.infrastructure.entity.Usuario;
import com.marcelo.aprendendospring.infrastructure.exceptions.ConflictException;
import com.marcelo.aprendendospring.infrastructure.exceptions.ResourceNotFoundException;
import com.marcelo.aprendendospring.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    /*@Autowired
    private UsuarioRepository usuarioRepository;*/
    private final UsuarioRepository usuarioRepository; //"final" garante que os atributos não sejam modificados
    private final PasswordEncoder passwordEncoder; //declarado para usar o método do Spring Security que faz a criptografia da senha

    public Usuario salvarUsuario(Usuario usuario) {
        try {
            emailExiste(usuario.getEmail());
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
            return usuarioRepository.save(usuario);
        } catch (ConflictException e) {
            throw new ConflictException("Email já cadastrado!", e.getCause());

        }

    }

    public void emailExiste(String email) {
        try {
            boolean existe = verificaEmailExistente(email);
            if(existe) {
                throw new ConflictException("Email já cadastrado!" + email);
            }
        } catch(ConflictException e) {
            throw new ConflictException("Email já cadastrado!" + e.getCause());
        }
    }

    public boolean verificaEmailExistente(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public Usuario buscarUsuarioporEmail(String email) {
        return usuarioRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("E-mail não encontrado" + email));
    }

    public void deletaUsuarioPorEmail(String email) {
        usuarioRepository.deleteByEmail(email);
    }


}
