package br.uninter.medalerta.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Endereco")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEndereco")
    private Integer idEndereco;

    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario usuario;

    @Column(name = "rua", length = 100)
    private String rua;

    @Column(name = "numero")
    private Integer numero;

    @Column(name = "complemento", length = 50)
    private String complemento;

    @Column(name = "bairro", length = 50)
    private String bairro;

    @Column(name = "CEP", length = 10)
    private String cep;

    @Column(name = "cidade", length = 50)
    private String cidade;

    @Column(name = "estado", length = 2)
    private String estado;

    public Endereco() {
    }

    public Integer getIdEndereco() { 
        return idEndereco;
     }
    public void setIdEndereco(Integer idEndereco) { 
        this.idEndereco = idEndereco;
     }

    public Usuario getUsuario() { 
        return usuario;
     }
    public void setUsuario(Usuario usuario) { 
        this.usuario = usuario;
     }

    public String getRua() { 
        return rua;
     }
    public void setRua(String rua) { 
        this.rua = rua;
     }

    public Integer getNumero() { 
        return numero;
     }
    public void setNumero(Integer numero) { 
        this.numero = numero;
     }

    public String getComplemento() { 
        return complemento;
     }
    public void setComplemento(String complemento) { 
        this.complemento = complemento;
     }

    public String getBairro() { 
        return bairro;
     }
    public void setBairro(String bairro) { 
        this.bairro = bairro;
     }

    public String getCep() { 
        return cep;
     }
    public void setCep(String cep) { 
        this.cep = cep;
     }

    public String getCidade() { 
        return cidade;
     }
    public void setCidade(String cidade) { 
        this.cidade = cidade;
     }

    public String getEstado() { 
        return estado;
     }
    public void setEstado(String estado) { 
        this.estado = estado;
     }

    @Override
    public String toString() {
        return "Endereco{" +
                "idEndereco=" + idEndereco +
                ", usuario=" + (usuario != null ? usuario.getIdUsuario() : null) +
                ", rua='" + rua + '\'' +
                ", numero=" + numero +
                ", complemento='" + complemento + '\'' +
                ", bairro='" + bairro + '\'' +
                ", cep='" + cep + '\'' +
                ", cidade='" + cidade + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}