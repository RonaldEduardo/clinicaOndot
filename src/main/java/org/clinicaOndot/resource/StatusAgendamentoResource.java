package org.clinicaOndot.resource;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.clinicaOndot.model.Agendamento;
import org.clinicaOndot.model.StatusAgendamento;

import java.util.List;
import java.util.Optional;

@Path("/statusAgendamento")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StatusAgendamentoResource {

    @POST
    @Transactional
    public Response criarStatusAgendamento(StatusAgendamento status) {
        status.persist();

        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    public List<StatusAgendamento> listarStatusAgendamentos() {
        return StatusAgendamento.listAll();
    }

    @GET
    @Path("/{id}")
    public Response listarStatusAgendamento(@PathParam("id") Long id) {
        Optional<StatusAgendamento> status = StatusAgendamento.findByIdOptional(id);

        if (status.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(status.get()).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response atualizarStatusAgendamento(@PathParam("id") Long id, StatusAgendamento statusAtualizado) {
        StatusAgendamento statusExistente = StatusAgendamento.findById(id);

        if (statusExistente == null) {
            // Se não encontrou, retorna 404 Not Found
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        statusExistente.setDescricao(statusAtualizado.getDescricao());
        // Não precisa de persist() aqui pois o Panache gerencia dentro da transação
        return Response.ok(statusExistente).build();
    }

    @DELETE // HTTP DELETE para remover um recurso
    @Path("/{id}")
    @Transactional
    public Response deletarStatusAgendamento(@PathParam("id") Long id) {
        StatusAgendamento status = StatusAgendamento.findById(id);

        if (status == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        // Se não houver agendamentos, realizar a exclusão física
        status.delete(); // Exclusão física usando Panache
        return Response.noContent().build(); // Retorna 204 No Content para sucesso de exclusão
    }
}
