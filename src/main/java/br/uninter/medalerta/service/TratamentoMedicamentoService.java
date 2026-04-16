package br.uninter.medalerta.service;

import br.uninter.medalerta.model.*;
import br.uninter.medalerta.repository.*;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TratamentoMedicamentoService {

    private final TratamentoMedicamentoRepository tratamentoMedicamentoRepository;
    private final TratamentoRepository tratamentoRepository;
    private final MedicamentoRepository medicamentoRepository;

    public TratamentoMedicamentoService(TratamentoMedicamentoRepository tratamentoMedicamentoRepository, TratamentoRepository tratamentoRepository, MedicamentoRepository medicamentoRepository) {
        this.tratamentoMedicamentoRepository = tratamentoMedicamentoRepository;
        this.tratamentoRepository = tratamentoRepository;
        this.medicamentoRepository = medicamentoRepository;
    }

    public TratamentoMedicamento associar(Integer idTratamento, Integer idMedicamento) {
        Tratamento tratamento = tratamentoRepository.findById(idTratamento)
                .orElseThrow(() -> new RuntimeException("Tratamento não encontrado com id: " + idTratamento));
        Medicamento medicamento = medicamentoRepository.findById(idMedicamento)
                .orElseThrow(() -> new RuntimeException("Medicamento não encontrado com id: " + idMedicamento));
        TratamentoMedicamento tm = new TratamentoMedicamento(tratamento, medicamento);
        return tratamentoMedicamentoRepository.save(tm);
    }

    public List<TratamentoMedicamento> listarPorTratamento(Integer idTratamento) {
        return tratamentoMedicamentoRepository.findByTratamento_IdTratamento(idTratamento);
    }

    public List<TratamentoMedicamento> listarPorMedicamento(Integer idMedicamento) {
        return tratamentoMedicamentoRepository.findByMedicamento_IdMedicamento(idMedicamento);
    }

    public Optional<TratamentoMedicamento> buscarPorId(Integer idTratamento, Integer idMedicamento) {
        TratamentoMedicamentoId id = new TratamentoMedicamentoId(idTratamento, idMedicamento);
        return tratamentoMedicamentoRepository.findById(id);
    }

    public void desassociar(Integer idTratamento, Integer idMedicamento) {
        TratamentoMedicamentoId id = new TratamentoMedicamentoId(idTratamento, idMedicamento);
        tratamentoMedicamentoRepository.deleteById(id);
    }
}
