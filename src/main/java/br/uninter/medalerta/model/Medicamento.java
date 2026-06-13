package br.uninter.medalerta.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "medicamento")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Medicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_medicamento")
    private Integer idMedicamento;

    @NotBlank
    @Size(max = 100)
    @Column(name = "nome_comercial", length = 100)
    private String nomeComercial;

    @Size(max = 100)
    @Column(name = "nome_generico", length = 100)
    private String nomeGenerico;

    @Size(max = 100)
    @Column(name = "forma_uso", length = 100)
    private String formaUso;

    @Size(max = 200)
    @Column(name = "observacao", length = 200)
    private String observacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "unidade_medida")
    private QuantidadeTipo unidadeMedida;
}
