package br.uninter.medalerta.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUsuario")
    private Integer idUsuario;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "telefone", nullable = false, length = 20)
    private String telefone;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Endereco> enderecos;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tratamento> tratamentos;

    public Usuario() {
    }

    public Integer getIdUsuario() { 
        return idUsuario; 
    }
    public void setIdUsuario(Integer idUsuario) { 
        this.idUsuario = idUsuario; 
    }

    public String getNome() { 
        return nome; 
    }
    public void setNome(String nome) { 
        this.nome = nome; 
    }

    public String getTelefone() { 
        return telefone;
     }
    public void setTelefone(String telefone) { 
        this.telefone = telefone;
     }

    public String getEmail() { 
        return email;
     }
    public void setEmail(String email) { 
        this.email = email;
     }

    public List<Endereco> getEnderecos() { 
        return enderecos;
     }
    public void setEnderecos(List<Endereco> enderecos) { 
        this.enderecos = enderecos;
     }

    public List<Tratamento> getTratamentos() { 
        return tratamentos;
     }
    public void setTratamentos(List<Tratamento> tratamentos) { 
        this.tratamentos = tratamentos;
     }

    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario=" + idUsuario +
                ", nome='" + nome + '\'' +
                ", telefone='" + telefone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}