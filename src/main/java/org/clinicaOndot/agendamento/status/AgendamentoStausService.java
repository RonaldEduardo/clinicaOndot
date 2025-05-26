package org.clinicaOndot.agendamento.status;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/agendamentoStatus")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AgendamentoStausService {
    @Inject
    AgendamentoStatusResource agendamentoStatusResource;

    @POST
    public Response criarStatusAgendamento(AgendamentoStatusRequestDto request) {
        AgendamentoStatus novoStatus = agendamentoStatusResource.criar(request);

        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    public List<AgendamentoStatus> listarStatusAgendamentos() {
        return agendamentoStatusResource.listar();
    }

    @GET
    @Path("/{id}")
    public Response listarAgendamentoStatus(@PathParam("id") Long id) {
        return agendamentoStatusResource.listarPorId(id)
                .map(status -> Response.ok(status).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @PUT
    @Path("/{id}")
    public Response atualizarStatusAgendamento(@PathParam("id") Long id, @Valid AgendamentoStatusRequestDto statusAtualizado) {
        return agendamentoStatusResource.atualizarPorId(id, statusAtualizado)
                .map(status -> Response.ok(status).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @DELETE // HTTP DELETE para remover um recurso
    @Path("/{id}")
    public Response deletarStatusAgendamento(@PathParam("id") Long id) {
        boolean deletado = agendamentoStatusResource.deletarPorId(id);
        if (!deletado) {
            return Response.status(Response.Status.NOT_FOUND).build(); // Não encontrado
        }
        return Response.status(Response.Status.NO_CONTENT).build(); // Sucesso, sem conteúdo
    }
}
