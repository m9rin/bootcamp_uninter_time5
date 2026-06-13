package br.uninter.medalerta.repository;

import br.uninter.medalerta.model.Tratamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


public interface TratamentoRepository extends JpaRepository<Tratamento, Integer> {
    List<Tratamento> findByUsuario_IdUsuario(Integer idUsuario);
}