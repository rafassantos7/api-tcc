package com.example.levelUp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tab_usuario_cadastrado")
public class UsuarioCadastrado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String emailUsuario;
    private String senhaUsuario;

    public void getEmailUsuario(String email){
        this.emailUsuario = email;
    }

    public void getSenhaUsuario(String senha){
        this.senhaUsuario = senha;
    }


    
}
