package org.clinicaOndot.agendamento;

import jakarta.enterprise.context.RequestScoped;
import jakarta.transaction.Transactional; // Importe para garantir operações de DB dentro de uma transação
import jakarta.ws.rs.*; // Importe as anotações JAX-RS
import jakarta.ws.rs.core.Response; // Para retornar respostas HTTP
import org.clinicaOndot.paciente.Paciente;
import org.clinicaOndot.statusAgendamento.StatusAgendamento;

import java.util.List;
import java.util.Optional; // Para lidar com a possibilidade de não encontrar um agendamento

@RequestScoped
public class AgendamentoResource {
    @Transactional
    public Response criar(AgendamentoRequestDto request) {
        Paciente paciente = Paciente.findById(request.getPacienteId());
        if (paciente == null) {
            // Se o paciente não for encontrado, retorna um erro 400 Bad Request
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Paciente com ID " + request.getPacienteId() + " não encontrado.")
                    .build();
        }
        StatusAgendamento status = StatusAgendamento.findById(request.getStatusAgendamentoId());
        if (status == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Status com ID " + request.getStatusAgendamentoId() + " não encontrado.")
                    .build();
        }
        Agendamento agendamento = new Agendamento();
        agendamento.setPaciente(paciente); // Associa o objeto Paciente encontrado
        agendamento.setDataHora(request.getDataHora());
        agendamento.setObservacoes(request.getObservacoes());
        agendamento.setStatus(status);

        agendamento.persist(); // usando o panche para salver os dados no banco
        // Retorna uma resposta 201 Created, com o agendamento salvo no corpo da resposta
        return Response.status(Response.Status.CREATED).entity(agendamento).build();
    }

    public List<Agendamento> listar() {
        return Agendamento.listAll();
    }

    public Response buscarPorId(Long id) {
        Optional<Agendamento> agendamento = Agendamento.findByIdOptional(id);

        if (agendamento.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(agendamento.get()).build();
    }

    @Transactional
    public Response atualizar(Long id, AgendamentoRequestDto request) {
        Agendamento agendamentoExistente = Agendamento.findById(id);

        if (agendamentoExistente == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if (request.getPacienteId() != null) {
            Paciente newPaciente = Paciente.findById(request.getPacienteId());
            if (newPaciente == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Paciente com ID " + request.getPacienteId() + " não encontrado para atualização.")
                        .build();
            }
            agendamentoExistente.setPaciente(newPaciente);
        }

        validaRequest(request, agendamentoExistente);

        return Response.ok(agendamentoExistente).build();
    }

    private static void validaRequest(AgendamentoRequestDto request, Agendamento agendamentoExistente) {

        if (request.getDataHora() != null) { // Adicionamos esta verificação!
            agendamentoExistente.setDataHora(request.getDataHora());
        }
        if (request.getObservacoes() != null) { // Opcional: fazer o mesmo para observacoes se ela também puder ser nula na requisição
            agendamentoExistente.setObservacoes(request.getObservacoes());
        }
    }

    @Transactional
    public Response deletar(Long id) {
        boolean deleted = Agendamento.deleteById(id);

        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.noContent().build();
    }
}
