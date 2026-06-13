package br.uninter.medalerta.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import java.time.LocalTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(
    name = "tratamento_medicamento",
    uniqueConstraints = @UniqueConstraint(
        name = "uk_tratamento_medicamento",
        columnNames = {"id_tratamento", "id_medicamento"}
    )
)
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"tratamento", "medicamento"})
public class TratamentoMedicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tratamento_medicamento")
    private Integer idTratamentoMedicamento;

    @ManyToOne
    @JoinColumn(name = "id_tratamento", nullable = false)
    private Tratamento tratamento;

    @ManyToOne
    @JoinColumn(name = "id_medicamento", nullable = false)
    private Medicamento medicamento;

    @Size(max = 50)
    @Column(name = "quantidade", length = 50)
    private String quantidade;

    @Size(max = 200)
    @Column(name = "observacao", length = 200)
    private String observacao;

    @Column(name = "horario_uso")
    private LocalTime horarioUso;

    @Size(max = 50)
    @Column(name = "frequencia_uso", length = 50)
    private String frequenciaUso;

    public TratamentoMedicamento(Tratamento tratamento, Medicamento medicamento, String quantidade, String observacao, LocalTime horarioUso, String frequenciaUso) {
        this.tratamento = tratamento;
        this.medicamento = medicamento;
        this.quantidade = quantidade;
        this.observacao = observacao;
        this.horarioUso = horarioUso;
        this.frequenciaUso = frequenciaUso;
    }
}
