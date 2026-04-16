package br.uninter.medalerta.model;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "medicamento")
public class Medicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_medicamento")
    private Integer idMedicamento;

    @Column(name = "nome_comercial", length = 100)
    private String nomeComercial;

    @Column(name = "nome_generico", length = 100)
    private String nomeGenerico;

    @Column(name = "forma_uso", length = 100)
    private String formaUso;

    @Column(name = "observacao", length = 200)
    private String observacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "quantidade")
    private QuantidadeTipo quantidade;

    @OneToMany(mappedBy = "medicamento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TratamentoMedicamento> tratamentos;

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

    public List<TratamentoMedicamento> getTratamentos() { 
        return tratamentos;
     }
    public void setTratamentos(List<TratamentoMedicamento> tratamentos) { 
        this.tratamentos = tratamentos;
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