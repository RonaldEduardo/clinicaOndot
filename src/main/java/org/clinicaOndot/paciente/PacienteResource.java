package org.clinicaOndot.paciente;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.Response;
import org.clinicaOndot.agendamento.Agendamento;
import org.clinicaOndot.operador.Operador;

import java.util.List;
import java.util.Optional;

@RequestScoped
public class PacienteResource {
    @Inject
    PacienteRepository pacienteRepository;

    @Transactional
    public Paciente criar(@Valid PacienteRequestDto request) {
        Paciente paciente = new Paciente();
        paciente.setAtivo(true);
        paciente.setNomeCompleto(request.getNomeCompleto());
        paciente.setDocumento(request.getDocumento());

        pacienteRepository.persist(paciente);

        return paciente;
    }

    public List<Paciente> listarTodos() {
        return pacienteRepository.listAll();
    }

    public Optional<Paciente> listarPorId(Long id) {
        return pacienteRepository.findByIdOptional(id);
    }

    @Transactional
    public Optional<Paciente> atualizarPorId(Long id,@Valid PacienteRequestDto pacienteRequestDto) {
        Optional<Paciente> pacienteExistenteOpt = pacienteRepository.findByIdOptional(id);

        if (pacienteExistenteOpt.isEmpty()) {
            // Se não encontrou, retorna false
            return Optional.empty();
        }
        Paciente pacienteExistente = pacienteExistenteOpt.get();
        atualizaDados(pacienteRequestDto, pacienteExistente);

        return Optional.of(pacienteExistente);
    }

    private static void atualizaDados(PacienteRequestDto request, Paciente pacienteExistente) {
        if(request.getNomeCompleto() != null){
            pacienteExistente.setNomeCompleto(request.getNomeCompleto());
        }

        if(request.getDocumento() != null){
            pacienteExistente.setDocumento(request.getDocumento());
        }
    }

    @Transactional
    public boolean deletarPorId(Long id) {
        return pacienteRepository.deleteById(id);
    }


}
