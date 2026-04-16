package br.uninter.medalerta.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Tratamento_Medicamento")
public class TratamentoMedicamento {

    @EmbeddedId
    private TratamentoMedicamentoId id;

    @ManyToOne
    @MapsId("idTratamento")
    @JoinColumn(name = "idTratamento", nullable = false)
    private Tratamento tratamento;

    @ManyToOne
    @MapsId("idMedicamento")
    @JoinColumn(name = "idMedicamento", nullable = false)
    private Medicamento medicamento;

    public TratamentoMedicamento() {}

    public TratamentoMedicamento(Tratamento tratamento, Medicamento medicamento) {
        this.tratamento = tratamento;
        this.medicamento = medicamento;
        this.id = new TratamentoMedicamentoId(tratamento.getIdTratamento(), medicamento.getIdMedicamento());
    }

    public TratamentoMedicamentoId getId() { return id; }
    public void setId(TratamentoMedicamentoId id) { this.id = id; }

    public Tratamento getTratamento() { return tratamento; }
    public void setTratamento(Tratamento tratamento) { this.tratamento = tratamento; }

    public Medicamento getMedicamento() { return medicamento; }
    public void setMedicamento(Medicamento medicamento) { this.medicamento = medicamento; }

    @Override
    public String toString() {
        return "TratamentoMedicamento{" +
                "idTratamento=" + (tratamento != null ? tratamento.getIdTratamento() : null) +
                ", idMedicamento=" + (medicamento != null ? medicamento.getIdMedicamento() : null) +
                '}';
    }
}