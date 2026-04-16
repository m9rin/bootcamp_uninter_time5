package br.uninter.medalerta.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Alerta")
public class Alerta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idAlerta")
    private Integer idAlerta;

    @ManyToOne
    @JoinColumn(name = "idTratamento", nullable = false)
    private Tratamento tratamento;

    @Column(name = "dataHorarioAlerta", nullable = false)
    private LocalDateTime dataHorarioAlerta;

    @Enumerated(EnumType.STRING)
    @Column(name = "statusAlerta")
    private StatusAlerta statusAlerta;

    @Column(name = "dataHorarioConsumo")
    private LocalDateTime dataHorarioConsumo;

    @Enumerated(EnumType.STRING)
    @Column(name = "confirmacaoConsumo")
    private ConfirmacaoConsumo confirmacaoConsumo;

    public Alerta() {
    }

    public Integer getIdAlerta() { 
        return idAlerta;
         }
    public void setIdAlerta(Integer idAlerta) { 
        this.idAlerta = idAlerta;
         }

    public Tratamento getTratamento() { 
        return tratamento;
         }
    public void setTratamento(Tratamento tratamento) { 
        this.tratamento = tratamento;
         }

    public LocalDateTime getDataHorarioAlerta() { 
        return dataHorarioAlerta;
         }
    public void setDataHorarioAlerta(LocalDateTime dataHorarioAlerta) { 
        this.dataHorarioAlerta = dataHorarioAlerta;
         }

    public StatusAlerta getStatusAlerta() { 
        return statusAlerta;
         }
    public void setStatusAlerta(StatusAlerta statusAlerta) { 
        this.statusAlerta = statusAlerta;
         }

    public LocalDateTime getDataHorarioConsumo() { 
        return dataHorarioConsumo;
         }
    public void setDataHorarioConsumo(LocalDateTime dataHorarioConsumo) { 
        this.dataHorarioConsumo = dataHorarioConsumo;
         }

    public ConfirmacaoConsumo getConfirmacaoConsumo() { 
        return confirmacaoConsumo;
         }
    public void setConfirmacaoConsumo(ConfirmacaoConsumo confirmacaoConsumo) { 
        this.confirmacaoConsumo = confirmacaoConsumo;
         }

    @Override
    public String toString() {
        return "Alerta{" +
                "idAlerta=" + idAlerta +
                ", tratamento=" + (tratamento != null ? tratamento.getIdTratamento() : null) +
                ", dataHorarioAlerta=" + dataHorarioAlerta +
                ", statusAlerta=" + statusAlerta +
                ", dataHorarioConsumo=" + dataHorarioConsumo +
                ", confirmacaoConsumo=" + confirmacaoConsumo +
                '}';
    }
}