package br.uninter.medalerta.repository;

import br.uninter.medalerta.model.TratamentoMedicamento;
import br.uninter.medalerta.model.TratamentoMedicamentoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TratamentoMedicamentoRepository extends JpaRepository<TratamentoMedicamento, TratamentoMedicamentoId> {
    List<TratamentoMedicamento> findByTratamento_IdTratamento(Integer idTratamento);
    List<TratamentoMedicamento> findByMedicamento_IdMedicamento(Integer idMedicamento);
}