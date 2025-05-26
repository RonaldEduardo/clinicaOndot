package org.clinicaOndot.agendamento;

import jakarta.inject.Inject; // Para injetar a camada de negócio
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
@Path("/agendamentos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AgendamentoService {
    @Inject // O CDI injeta uma instância da camada de negócio aqui
    AgendamentoResource agendamentoResource;

    @POST
    @Transactional
    public Response criarAgendamento(AgendamentoRequestDto request) {
        return agendamentoResource.criar(request);
    }

    @GET
    public List<Agendamento> listarAgendamento() {
        return agendamentoResource.listar();
    }

    @GET
    @Path("/{id}")
    public Response buscarAgendamentoPorId(@PathParam("id") Long id) {
        return  agendamentoResource.buscarPorId(id);
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response atualizarAgendamento(@PathParam("id") Long id, AgendamentoRequestDto request) {
        return  agendamentoResource.atualizar(id, request);
    }

    @DELETE // HTTP DELETE para remover um recurso
    @Path("/{id}")
    @Transactional
    public Response deletarAgendamento(@PathParam("id") Long id) {
        return agendamentoResource.deletar(id);
    }
}
