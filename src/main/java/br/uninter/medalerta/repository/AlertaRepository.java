package br.uninter.medalerta.repository;

import br.uninter.medalerta.model.Alerta;
import br.uninter.medalerta.model.StatusAlerta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AlertaRepository extends JpaRepository<Alerta, Integer> {
    List<Alerta> findByTratamento_IdTratamento(Integer idTratamento);
    List<Alerta> findByStatusAlerta(StatusAlerta statusAlerta);
}