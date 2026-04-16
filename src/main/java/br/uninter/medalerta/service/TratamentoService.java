package br.uninter.medalerta.service;

import br.uninter.medalerta.model.Tratamento;
import br.uninter.medalerta.model.Usuario;
import br.uninter.medalerta.repository.TratamentoRepository;
import br.uninter.medalerta.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TratamentoService {

    private TratamentoRepository tratamentoRepository;
    private UsuarioRepository usuarioRepository;

    public TratamentoService(TratamentoRepository tratamentoRepository, UsuarioRepository usuarioRepository) {
        this.tratamentoRepository = tratamentoRepository;
        this.usuarioRepository = usuarioRepository;
    }

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
        Optional<Tratamento> tratamento = tratamentoRepository.findById(id);
        if (tratamento.isPresent()) {
            return tratamento;
        } else {
            throw new RuntimeException("Tratamento não encontrado com id: " + id);
        }
    }

    public Tratamento atualizar(Integer id, Tratamento dadosAtualizados) {
        return tratamentoRepository.findById(id).map(tratamento -> {
            tratamento.setHorarioUso(dadosAtualizados.getHorarioUso());
            tratamento.setFrequenciaUso(dadosAtualizados.getFrequenciaUso());
            tratamento.setAlertas(dadosAtualizados.getAlertas());
            tratamento.setMedicamentos(dadosAtualizados.getMedicamentos());
            return tratamentoRepository.save(tratamento);
        }).orElseThrow(() -> new RuntimeException("Tratamento não encontrado com id: " + id));
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