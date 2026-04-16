package br.uninter.medalerta.service;

import br.uninter.medalerta.model.Medicamento;
import br.uninter.medalerta.repository.MedicamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MedicamentoService {

    private final MedicamentoRepository medicamentoRepository;

    public MedicamentoService(MedicamentoRepository medicamentoRepository) {
        this.medicamentoRepository = medicamentoRepository;
    }

    public Medicamento salvar(Medicamento medicamento) {
        return medicamentoRepository.save(medicamento);
    }

    public List<Medicamento> listarTodos() {
        return medicamentoRepository.findAll();
    }

    public Optional<Medicamento> buscarPorId(Integer id) {
        return medicamentoRepository.findById(id);
    }

    public List<Medicamento> buscarPorNomeComercial(String nome) {
        return medicamentoRepository.findByNomeComercialContainingIgnoreCase(nome);
    }

    public List<Medicamento> buscarPorNomeGenerico(String nome) {
        return medicamentoRepository.findByNomeGenericoContainingIgnoreCase(nome);
    }

    public Medicamento atualizar(Integer id, Medicamento dadosAtualizados) {
        return medicamentoRepository.findById(id).map(medicamento -> {
            medicamento.setNomeComercial(dadosAtualizados.getNomeComercial());
            medicamento.setNomeGenerico(dadosAtualizados.getNomeGenerico());
            medicamento.setQuantidade(dadosAtualizados.getQuantidade());
            medicamento.setFormaUso(dadosAtualizados.getFormaUso());
            medicamento.setObservacao(dadosAtualizados.getObservacao());
            return medicamentoRepository.save(medicamento);
        }).orElseThrow(() -> new RuntimeException("Medicamento não encontrado com id: " + id));
    }

    public void deletar(Integer id) {
        Optional<Medicamento> medicamento = medicamentoRepository.findById(id);
        if (medicamento.isPresent()) {
            medicamentoRepository.deleteById(id);
        } else {
            throw new RuntimeException("Medicamento não encontrado com id: " + id);
        }
    }
}