package org.clinicaOndot.agendamento;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional; // Importe para garantir operações de DB dentro de uma transação
import jakarta.validation.Valid;
import jakarta.ws.rs.BadRequestException;
import org.clinicaOndot.agendamento.status.AgendamentoStatusRepository;
import org.clinicaOndot.paciente.Paciente;
import org.clinicaOndot.agendamento.status.AgendamentoStatus;
import org.clinicaOndot.paciente.PacienteRepository;

import java.util.List;
import java.util.Optional; // Para lidar com a possibilidade de não encontrar um agendamento

@RequestScoped
public class AgendamentoResource {
    @Inject
    AgendamentoRepository agendamentoRepository;
    @Inject
    PacienteRepository pacienteRepository;
    @Inject
    AgendamentoStatusRepository agendamentoStatusRepository;

    @Transactional
    public Agendamento criar(@Valid AgendamentoRequestDto request) {
        Paciente paciente = pacienteRepository.findById(request.getPacienteId());
        if (paciente == null) {
            throw new BadRequestException("Paciente com ID " + request.getPacienteId() + " não encontrado.");
        }
        AgendamentoStatus status = agendamentoStatusRepository.findById(request.getStatusAgendamentoId());
        if (status == null) {
            throw new BadRequestException("Status com ID " + request.getStatusAgendamentoId() + " não encontrado.");
        }
        Agendamento novoAgendamento = new Agendamento();
        novoAgendamento.setPaciente(paciente); // Associa o objeto Paciente encontrado
        novoAgendamento.setStatus(status);
        novoAgendamento.setDataHora(request.getDataHora());
        novoAgendamento.setObservacoes(request.getObservacoes());

        agendamentoRepository.persist(novoAgendamento); // usando o panche para salver os dados no banco

        return novoAgendamento;
    }

    public List<Agendamento> listar() {
        return agendamentoRepository.listAll();
    }

    public Optional<Agendamento> buscarPorId(Long id) {
        return agendamentoRepository.findByIdOptional(id);
    }

    @Transactional
    public Optional<Agendamento> atualizarPorId(Long id, @Valid AgendamentoRequestDto request) {
        Optional<Agendamento> agendamentoExistenteOpt = agendamentoRepository.findByIdOptional(id);

        if(agendamentoExistenteOpt.isEmpty()){
            return Optional.empty();
        }

        Agendamento agendamentoExistente = agendamentoExistenteOpt.get(); // Agora sabemos que existe

        // 2. Atualiza o Paciente, se fornecido no request
        if (request.getPacienteId() != null) {
            Paciente novoPaciente = pacienteRepository.findByIdOptional(request.getPacienteId())
                    .orElseThrow(() -> new BadRequestException("Paciente com ID " + request.getPacienteId() + " não encontrado para atualização."));
            agendamentoExistente.setPaciente(novoPaciente);
        }

        // 3. Atualiza o Status do Agendamento, se fornecido no request
        if (request.getStatusAgendamentoId() != null) {
            AgendamentoStatus novoStatus = agendamentoStatusRepository.findByIdOptional(request.getStatusAgendamentoId())
                    .orElseThrow(() -> new BadRequestException("Status de agendamento com ID " + request.getStatusAgendamentoId() + " não encontrado para atualização."));
            agendamentoExistente.setStatus(novoStatus);
        }

        atualizaDados(request, agendamentoExistente);

        return Optional.of(agendamentoExistente);
    }

    private static void atualizaDados(AgendamentoRequestDto request, Agendamento agendamentoExistente) {

        if (request.getDataHora() != null) { // Adicionamos esta verificação!
            agendamentoExistente.setDataHora(request.getDataHora());
        }
        if (request.getObservacoes() != null) { // Opcional: fazer o mesmo para observacoes se ela também puder ser nula na requisição
            agendamentoExistente.setObservacoes(request.getObservacoes());
        }
    }

    @Transactional
    public boolean deletarPorId(Long id) {
       return agendamentoRepository.deleteById(id);
    }
}
