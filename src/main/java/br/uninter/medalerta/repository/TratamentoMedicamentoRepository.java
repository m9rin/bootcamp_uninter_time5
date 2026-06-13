package br.uninter.medalerta.repository;

import br.uninter.medalerta.model.TratamentoMedicamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


public interface TratamentoMedicamentoRepository extends JpaRepository<TratamentoMedicamento, Integer> {
    List<TratamentoMedicamento> findByTratamento_IdTratamento(Integer idTratamento);
    List<TratamentoMedicamento> findByMedicamento_IdMedicamento(Integer idMedicamento);
    Optional<TratamentoMedicamento> findByTratamento_IdTratamentoAndMedicamento_IdMedicamento(Integer idTratamento, Integer idMedicamento);
}
