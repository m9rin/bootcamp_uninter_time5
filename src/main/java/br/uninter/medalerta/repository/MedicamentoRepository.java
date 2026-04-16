package br.uninter.medalerta.repository;

import br.uninter.medalerta.model.Medicamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MedicamentoRepository extends JpaRepository<Medicamento, Integer> {
    List<Medicamento> findByNomeComercialContainingIgnoreCase(String nomeComercial);
    List<Medicamento> findByNomeGenericoContainingIgnoreCase(String nomeGenerico);
}