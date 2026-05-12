package com.marcelo.aprendendospring.infrastructure.repository;

import com.marcelo.aprendendospring.infrastructure.entity.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByEmail(String email); //método que recebe um e-mail, consulta o banco e retorna um boolean dizendo se já existe ou não;

    Optional<Usuario> findByEmail(String email);
    //Optional serve para dar um tratamento mais adequado para o retorno de valores nulos.
    //Numa implementação normal, caso o email informado não seja encontrado no banco de dados,
    //seria necessário tratar essa condição com um Exception, provavelmente.
    //Com o Optional, é possível disparar uma Exception direto na classe que chama o método.

    @Transactional //ajuda a não causar nenhum erro na transação
    void deleteByEmail(String email);

}
