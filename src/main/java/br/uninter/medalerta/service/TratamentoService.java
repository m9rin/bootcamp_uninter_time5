package br.uninter.medalerta.service;

import br.uninter.medalerta.model.Medicamento;
import br.uninter.medalerta.model.Tratamento;
import br.uninter.medalerta.model.TratamentoMedicamento;
import br.uninter.medalerta.model.Usuario;
import br.uninter.medalerta.repository.MedicamentoRepository;
import br.uninter.medalerta.repository.TratamentoMedicamentoRepository;
import br.uninter.medalerta.repository.TratamentoRepository;
import br.uninter.medalerta.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TratamentoService {

    private final TratamentoRepository tratamentoRepository;
    private final UsuarioRepository usuarioRepository;
    private final MedicamentoRepository medicamentoRepository;
    private final TratamentoMedicamentoRepository tratamentoMedicamentoRepository;

    public Tratamento salvar(Integer idUsuario, Tratamento tratamento) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com id: " + idUsuario));
        tratamento.setUsuario(usuario);
        return tratamentoRepository.save(tratamento);
    }

    public List<Tratamento> listarTodos() {
        return tratamentoRepository.findAll();
    }

    public List<Tratamento> listarPorUsuario(Integer idUsuario) {
        return tratamentoRepository.findByUsuario_IdUsuario(idUsuario);
    }

    public Optional<Tratamento> buscarPorId(Integer id) {
        return tratamentoRepository.findById(id);
    }

    public Tratamento atualizar(Integer id, Tratamento dadosAtualizados) {
        return tratamentoRepository.findById(id).map(tratamento -> {
            tratamento.setDescricao(dadosAtualizados.getDescricao());
            tratamento.setDataInicio(dadosAtualizados.getDataInicio());
            tratamento.setDataFim(dadosAtualizados.getDataFim());
            tratamento.setStatus(dadosAtualizados.getStatus());
            return tratamentoRepository.save(tratamento);
        }).orElseThrow(() -> new RuntimeException("Tratamento não encontrado com id: " + id));
    }

    public TratamentoMedicamento associarMedicamento(
            Integer idTratamento,
            Integer idMedicamento,
            String quantidade,
            String observacao,
            LocalTime horarioUso,
            String frequenciaUso
    ) {
        Tratamento tratamento = tratamentoRepository.findById(idTratamento)
                .orElseThrow(() -> new RuntimeException("Tratamento não encontrado com id: " + idTratamento));
        Medicamento medicamento = medicamentoRepository.findById(idMedicamento)
                .orElseThrow(() -> new RuntimeException("Medicamento não encontrado com id: " + idMedicamento));

        tratamentoMedicamentoRepository
                .findByTratamento_IdTratamentoAndMedicamento_IdMedicamento(idTratamento, idMedicamento)
                .ifPresent(tm -> {
                    throw new RuntimeException("Medicamento já associado a este tratamento.");
                });

        TratamentoMedicamento tm = new TratamentoMedicamento(
                tratamento,
                medicamento,
                quantidade,
                observacao,
                horarioUso,
                frequenciaUso
        );
        return tratamentoMedicamentoRepository.save(tm);
    }

    public List<TratamentoMedicamento> listarMedicamentos(Integer idTratamento) {
        return tratamentoMedicamentoRepository.findByTratamento_IdTratamento(idTratamento);
    }

    public void desassociarMedicamento(Integer idTratamento, Integer idMedicamento) {
        TratamentoMedicamento tm = tratamentoMedicamentoRepository
                .findByTratamento_IdTratamentoAndMedicamento_IdMedicamento(idTratamento, idMedicamento)
                .orElseThrow(() -> new RuntimeException("Medicamento não associado a este tratamento."));
        tratamentoMedicamentoRepository.delete(tm);
    }

    public void deletar(Integer id) {
        Optional<Tratamento> tratamento = tratamentoRepository.findById(id);
        if (tratamento.isPresent()) {
            tratamentoRepository.deleteById(id);
        } else {
            throw new RuntimeException("Tratamento não encontrado com id: " + id);
        }
    }
}
