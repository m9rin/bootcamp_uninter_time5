package br.uninter.medalerta.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Medicamento")
public class Medicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idMedicamento")
    private Integer idMedicamento;

    @Column(name = "nomeComercial", length = 100)
    private String nomeComercial;

    @Column(name = "nomeGenerico", length = 100)
    private String nomeGenerico;

    @Column(name = "formaUso", length = 100)
    private String formaUso;

    @Column(name = "observacao", length = 200)
    private String observacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "quantidade")
    private QuantidadeTipo quantidade;

    public Medicamento() {
    }

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

    public QuantidadeTipo getQuantidade() { 
        return quantidade;
         }
    public void setQuantidade(QuantidadeTipo quantidade) { 
        this.quantidade = quantidade;
         }

    @Override
    public String toString() {
        return "Medicamento{" +
                "idMedicamento=" + idMedicamento +
                ", nomeComercial='" + nomeComercial + '\'' +
                ", nomeGenerico='" + nomeGenerico + '\'' +
                ", formaUso='" + formaUso + '\'' +
                ", observacao='" + observacao + '\'' +
                ", quantidade=" + quantidade +
                '}';
    }
}