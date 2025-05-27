package org.clinicaOndot.agendamento.status;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@RequestScoped
public class AgendamentoStatusResource {
    @Inject
    AgendamentoStatusRepository agendamentoStatusRepository;

    @Transactional
    public AgendamentoStatus criar(@Valid AgendamentoStatusRequestDto request) {
        AgendamentoStatus agendamentoStatus = new AgendamentoStatus();
        agendamentoStatus.setDescricao(request.getDescricao());
        agendamentoStatusRepository.persist(agendamentoStatus);

        return  agendamentoStatus;
    }

    public List<AgendamentoStatus> listar() {
        return agendamentoStatusRepository.listAll();
    }

    public Optional<AgendamentoStatus>  listarPorId(Long id) {
        return agendamentoStatusRepository.findByIdOptional(id);
    }

    @Transactional
    public Optional<AgendamentoStatus> atualizarPorId(Long id, AgendamentoStatusRequestDto statusAtualizadoDto ) {
        Optional<AgendamentoStatus> statusExistenteOpt = agendamentoStatusRepository.findByIdOptional(id);

        if (statusExistenteOpt.isEmpty()) {
            // Se não encontrou, retorna false
            return Optional.empty();
        }
        AgendamentoStatus statusExistente = statusExistenteOpt.get();
        statusExistente.setDescricao(statusAtualizadoDto.getDescricao());

        return Optional.of(statusExistente);
    }

    @Transactional
    public boolean deletarPorId(Long id) {
        return  agendamentoStatusRepository.deleteById(id);
    }
}
