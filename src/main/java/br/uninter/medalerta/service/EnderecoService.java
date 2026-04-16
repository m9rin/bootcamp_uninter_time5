package br.uninter.medalerta.service;

import br.uninter.medalerta.model.Endereco;
import br.uninter.medalerta.model.Usuario;
import br.uninter.medalerta.repository.EnderecoRepository;
import br.uninter.medalerta.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final UsuarioRepository usuarioRepository;

    public EnderecoService(EnderecoRepository enderecoRepository, UsuarioRepository usuarioRepository) {
        this.enderecoRepository = enderecoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Endereco salvar(Integer idUsuario, Endereco endereco) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com id: " + idUsuario));
        endereco.setUsuario(usuario);
        return enderecoRepository.save(endereco);
    }

    public List<Endereco> listarPorUsuario(Integer idUsuario) {
        return enderecoRepository.findByUsuario_IdUsuario(idUsuario);
    }

    public Optional<Endereco> buscarPorId(Integer id) {
        Optional<Endereco> endereco = enderecoRepository.findById(id);
        if (endereco.isPresent()) {
            return endereco;
        } else {
            throw new RuntimeException("Endereço não encontrado com id: " + id);
        }
    }

    public Endereco atualizar(Integer id, Endereco dadosAtualizados) {
        return enderecoRepository.findById(id).map(endereco -> {
            endereco.setRua(dadosAtualizados.getRua());
            endereco.setNumero(dadosAtualizados.getNumero());
            endereco.setComplemento(dadosAtualizados.getComplemento());
            endereco.setBairro(dadosAtualizados.getBairro());
            endereco.setCep(dadosAtualizados.getCep());
            endereco.setCidade(dadosAtualizados.getCidade());
            endereco.setEstado(dadosAtualizados.getEstado());
            return enderecoRepository.save(endereco);
        }).orElseThrow(() -> new RuntimeException("Endereço não encontrado com id: " + id));
    }

    public void deletar(Integer id) {
        Optional<Endereco> endereco = enderecoRepository.findById(id);
        if (endereco.isPresent()) {
            enderecoRepository.deleteById(id);
        } else {
            throw new RuntimeException("Endereço não encontrado com id: " + id);
        }
    }
}