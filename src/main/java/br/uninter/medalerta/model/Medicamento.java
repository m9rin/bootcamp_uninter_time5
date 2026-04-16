package br.uninter.medalerta.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Medicamento")
public class Medicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdMedcimaento")
    private Integer idMedicamento;

    @Column(name = "NomeComercial", length = 100)
    private String nomeComercial;

    @Column(name = "NomeGenerico", length = 100)
    private String nomeGenerico;

    @Enumerated(EnumType.STRING)
    @Column(name = "Quantidade")
    private QuantidadeTipo quantidade;

    @Column(name = "FormaUso", length = 100)
    private String formaUso;

    @Column(name = "Observacao", length = 200)
    private String observacao;

    public Integer getIdMedicamento() { 
        return idMedicamento; 
        }
    public void setIdMedicamento(Integer idMedicamento) { 
        this.idMedicamento = idMedicamento; 
        }

    public String getNomeComercial() { 
        return nomeComercial; 
        }
    public void setNomeComercial(String nomeComercial) { 
        this.nomeComercial = nomeComercial; 
        }

    public String getNomeGenerico() { 
        return nomeGenerico; 
        }
    public void setNomeGenerico(String nomeGenerico) { 
        this.nomeGenerico = nomeGenerico;
         }

    public QuantidadeTipo getQuantidade() { 
        return quantidade; 
        }
    public void setQuantidade(QuantidadeTipo quantidade) { 
        this.quantidade = quantidade; 
        }

    public String getFormaUso() { 
        return formaUso; 
        }
    public void setFormaUso(String formaUso) { 
        this.formaUso = formaUso; 
        }

    public String getObservacao() { 
        return observacao; 
        }
    public void setObservacao(String observacao) { 
        this.observacao = observacao; 
        }

    @Override
    public String toString() {
        return "Medicamento{" +
                "idMedicamento=" + idMedicamento +
                ", nomeComercial='" + nomeComercial + '\'' +
                ", nomeGenerico='" + nomeGenerico + '\'' +
                ", quantidade=" + quantidade +
                ", formaUso='" + formaUso + '\'' +
                ", observacao='" + observacao + '\'' +
                '}';
    }
}