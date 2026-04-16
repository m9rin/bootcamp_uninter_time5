package br.uninter.medalerta.repository;

import br.uninter.medalerta.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {
    List<Endereco> findByUsuario_IdUsuario(Integer idUsuario);
}