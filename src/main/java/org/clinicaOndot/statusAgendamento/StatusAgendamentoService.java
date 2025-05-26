package org.clinicaOndot.statusAgendamento;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

@Path("/statusAgendamento")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StatusAgendamentoService {
    @Inject
    StatusAgendamentoResource statusAgendamentoResource;

    @POST
    public Response criarStatusAgendamento(StatusAgendamento request) {
        return statusAgendamentoResource.criar(request);
    }

    @GET
    public List<StatusAgendamento> listarStatusAgendamentos() {
        return statusAgendamentoResource.listar();
    }

    @GET
    @Path("/{id}")
    public Response listarStatusAgendamento(@PathParam("id") Long id) {
        return statusAgendamentoResource.listarPorId(id);
    }

    @PUT
    @Path("/{id}")
    public Response atualizarStatusAgendamento(@PathParam("id") Long id,@Valid StatusAgendamento statusAtualizado) {
        return statusAgendamentoResource.atualizarPorId(id, statusAtualizado);
    }

    @DELETE // HTTP DELETE para remover um recurso
    @Path("/{id}")
    public Response deletarStatusAgendamento(@PathParam("id") Long id) {
       return statusAgendamentoResource.deletarPorId(id);
    }
}
