package br.uninter.medalerta.model;

import jakarta.persistence.*;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "Tratamento")
public class Tratamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTratamento")
    private Integer idTratamento;

    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario usuario;

    @Column(name = "horarioUso")
    private LocalTime horarioUso;

    @Column(name = "frequenciaUso", length = 50)
    private String frequenciaUso;

    @OneToMany(mappedBy = "tratamento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Alerta> alertas;

    public Tratamento() {
    }

    public Integer getIdTratamento() { 
        return idTratamento;
         }
    public void setIdTratamento(Integer idTratamento) { 
        this.idTratamento = idTratamento;
         }

    public Usuario getUsuario() { 
        return usuario;
         }
    public void setUsuario(Usuario usuario) { 
        this.usuario = usuario;
         }

    public LocalTime getHorarioUso() { 
        return horarioUso;
         }
    public void setHorarioUso(LocalTime horarioUso) { 
        this.horarioUso = horarioUso;
         }

    public String getFrequenciaUso() { 
        return frequenciaUso;
         }
    public void setFrequenciaUso(String frequenciaUso) { 
        this.frequenciaUso = frequenciaUso;
         }

    public List<Alerta> getAlertas() { 
        return alertas;
         }
    public void setAlertas(List<Alerta> alertas) { 
        this.alertas = alertas;
         }

    @Override
    public String toString() {
        return "Tratamento{" +
                "idTratamento=" + idTratamento +
                ", usuario=" + (usuario != null ? usuario.getIdUsuario() : null) +
                ", horarioUso=" + horarioUso +
                ", frequenciaUso='" + frequenciaUso + '\'' +
                '}';
    }
}