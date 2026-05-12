package com.marcelo.aprendendospring.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuario")
public class Usuario implements UserDetails { //implements UserDetails é para ativar o Spring Security - Necessário implementar os métodos obrigatórios.

    @Id //chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) //gera id automaticamente
    private Long id;

    @Column(name = "nome", length = 100)
    private String nome;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "senha")
    private String senha;

    @OneToMany(cascade = CascadeType.ALL) //um usuário para muitos endereços
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private List<Endereco> enderecos;

    @OneToMany(cascade = CascadeType.ALL) //um usuário para muitos telefones
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private List<Telefone> telefones;

    //Métodos implementados pelo Spring Security
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public @Nullable String getPassword() {
        return senha; //a declaração vem vazia por padrão. necessário especificar o atributo da senha manualmente.
    }

    @Override
    public String getUsername() {
        return email; //a declaração vem vazia por padrão. necessário especificar o atributo do usuário manualmente, que no caso será o email.
    }
}
