package br.uninter.medalerta.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class TratamentoMedicamentoId implements Serializable {

    @Column(name = "idTratamento")
    private Integer idTratamento;

    @Column(name = "idMedicamento")
    private Integer idMedicamento;

    public TratamentoMedicamentoId() {}

    public TratamentoMedicamentoId(Integer idTratamento, Integer idMedicamento) {
        this.idTratamento = idTratamento;
        this.idMedicamento = idMedicamento;
    }

    public Integer getIdTratamento() { return idTratamento; }
    public void setIdTratamento(Integer idTratamento) { this.idTratamento = idTratamento; }

    public Integer getIdMedicamento() { return idMedicamento; }
    public void setIdMedicamento(Integer idMedicamento) { this.idMedicamento = idMedicamento; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TratamentoMedicamentoId)) return false;
        TratamentoMedicamentoId that = (TratamentoMedicamentoId) o;
        return Objects.equals(idTratamento, that.idTratamento) &&
               Objects.equals(idMedicamento, that.idMedicamento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTratamento, idMedicamento);
    }

    @Override
    public String toString() {
        return "TratamentoMedicamentoId{" +
                "idTratamento=" + idTratamento +
                ", idMedicamento=" + idMedicamento +
                '}';
    }
}