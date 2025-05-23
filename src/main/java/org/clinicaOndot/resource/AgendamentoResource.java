package org.clinicaOndot.resource;

import org.clinicaOndot.dto.AgendamentoRequest;
import org.clinicaOndot.model.Agendamento; // Importe a entidade que criamos
import jakarta.transaction.Transactional; // Importe para garantir operações de DB dentro de uma transação
import jakarta.ws.rs.*; // Importe as anotações JAX-RS
import jakarta.ws.rs.core.MediaType; // Para definir o tipo de conteúdo (JSON)
import jakarta.ws.rs.core.Response; // Para retornar respostas HTTP
import org.clinicaOndot.model.Paciente;
import org.clinicaOndot.model.StatusAgendamento;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional; // Para lidar com a possibilidade de não encontrar um agendamento

@Path("/agendamentos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AgendamentoResource {
    @POST
    @Transactional
    public Response criarAgendamento(AgendamentoRequest request) {
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

    @GET
    public List<Agendamento> listarAgendamento() {
        return Agendamento.listAll();
    }

    @GET
    @Path("/{id}")
    public Response buscarAgendamentoPorId(@PathParam("id") Long id) {
        Optional<Agendamento> agendamento = Agendamento.findByIdOptional(id);

        if (agendamento.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(agendamento.get()).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response atualizarAgendamento(@PathParam("id") Long id, AgendamentoRequest request) {
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

        // **MUDANÇA AQUI NO PUT:**
        if (request.getStatusAgendamentoId() != null) { // <--- Verificar se o ID não é nulo
            StatusAgendamento newStatus = StatusAgendamento.findById(request.getStatusAgendamentoId()); // <--- Passar o ID numérico
            if (newStatus == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Status com ID " + request.getStatusAgendamentoId() + " não encontrado para atualização.") // Use o ID correto
                        .build();
            }
            agendamentoExistente.setStatus(newStatus);
        }
        if (request.getDataHora() != null) { // Adicionamos esta verificação!
            agendamentoExistente.setDataHora(request.getDataHora());
        }
        if (request.getObservacoes() != null) { // Opcional: fazer o mesmo para observacoes se ela também puder ser nula na requisição
            agendamentoExistente.setObservacoes(request.getObservacoes());
        }

        return Response.ok(agendamentoExistente).build();
    }

    @DELETE // HTTP DELETE para remover um recurso
    @Path("/{id}")
    @Transactional
    public Response deletarAgendamento(@PathParam("id") Long id) {
        boolean deleted = Agendamento.deleteById(id);

        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.noContent().build();
    }
}
