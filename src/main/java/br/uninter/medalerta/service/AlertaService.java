package br.uninter.medalerta.service;

import br.uninter.medalerta.model.Alerta;
import br.uninter.medalerta.model.StatusAlerta;
import br.uninter.medalerta.model.Tratamento;
import br.uninter.medalerta.repository.AlertaRepository;
import br.uninter.medalerta.repository.TratamentoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AlertaService {

    private AlertaRepository alertaRepository;
    private TratamentoRepository tratamentoRepository;

    public AlertaService(AlertaRepository alertaRepository, TratamentoRepository tratamentoRepository) {
        this.alertaRepository = alertaRepository;
        this.tratamentoRepository = tratamentoRepository;
    }

    public Alerta salvar(Integer idTratamento, Alerta alerta) {
        Tratamento tratamento = tratamentoRepository.findById(idTratamento)
                .orElseThrow(() -> new RuntimeException("Tratamento não encontrado com id: " + idTratamento));
        alerta.setTratamento(tratamento);
        return alertaRepository.save(alerta);
    }

    public List<Alerta> listarTodos() {
        return alertaRepository.findAll();
    }

    public List<Alerta> listarPorTratamento(Integer idTratamento) {
        return alertaRepository.findByTratamento_IdTratamento(idTratamento);
    }

    public List<Alerta> listarPorStatus(StatusAlerta status) {
        return alertaRepository.findByStatusAlerta(status);
    }

    public Optional<Alerta> buscarPorId(Integer id) {
        Optional<Alerta> alerta = alertaRepository.findById(id);
        if (alerta.isPresent()) {
            return alerta;
        } else {
            throw new RuntimeException("Alerta não encontrado com id: " + id);
        }
    }

    public Alerta atualizar(Integer id, Alerta dadosAtualizados) {
        return alertaRepository.findById(id).map(alerta -> {
            alerta.setDataHorarioAlerta(dadosAtualizados.getDataHorarioAlerta());
            alerta.setStatusAlerta(dadosAtualizados.getStatusAlerta());
            alerta.setDataHorarioConsumo(dadosAtualizados.getDataHorarioConsumo());
            alerta.setConfirmacaoConsumo(dadosAtualizados.getConfirmacaoConsumo());
            return alertaRepository.save(alerta);
        }).orElseThrow(() -> new RuntimeException("Alerta não encontrado com id: " + id));
    }

    public void deletar(Integer id) {
        Optional<Alerta> alerta = alertaRepository.findById(id);
        if (alerta.isPresent()) {
            alertaRepository.deleteById(id);
        } else {
            throw new RuntimeException("Alerta não encontrado com id: " + id);
        }
    }
}
