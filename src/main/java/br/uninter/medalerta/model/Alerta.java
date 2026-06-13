package br.uninter.medalerta.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "alerta")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "tratamento")
public class Alerta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alerta")
    private Integer idAlerta;

    @ManyToOne
    @JoinColumn(name = "id_tratamento", nullable = false)
    private Tratamento tratamento;

    @NotNull
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
}
