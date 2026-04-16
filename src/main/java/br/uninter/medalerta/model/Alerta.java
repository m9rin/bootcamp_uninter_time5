package br.uninter.medalerta.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "alerta")
public class Alerta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alerta")
    private Integer idAlerta;

    @ManyToOne
    @JoinColumn(name = "id_tratamento", nullable = false)
    private Tratamento tratamento;

    @Column(name = "data_horario_alerta", nullable = false)
    private LocalDateTime dataHorarioAlerta;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_alerta")
    private StatusAlerta statusAlerta;

    @Column(name = "data_horario_consumo")
    private LocalDateTime dataHorarioConsumo;

    @Enumerated(EnumType.STRING)
    @Column(name = "confirmacao_consumo")
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