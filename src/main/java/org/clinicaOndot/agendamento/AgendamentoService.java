package org.clinicaOndot.agendamento;

import jakarta.inject.Inject; // Para injetar a camada de negócio
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

@Path("/agendamentos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AgendamentoService {
    @Inject // O CDI injeta uma instância da camada de negócio aqui
    AgendamentoResource agendamentoResource;

    @POST
    @Transactional
    public Response criarAgendamento(AgendamentoRequestDto request) {
        agendamentoResource.criar(request);
        
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    public List<Agendamento> listarAgendamento() {
        return agendamentoResource.listar();
    }

    @GET
    @Path("/{id}")
    public Response ListarAgendamentoPorId(@PathParam("id") Long id) {
        Optional<Agendamento> agendamento = agendamentoResource.buscarPorId(id);
        return Response.ok(agendamento.get()).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response atualizarAgendamento(@PathParam("id") Long id, AgendamentoRequestDto request) {
        return agendamentoResource.atualizarPorId(id, request)
                .map(agendamento -> Response.ok(agendamento).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @DELETE // HTTP DELETE para remover um recurso
    @Path("/{id}")
    @Transactional
    public Response deletarAgendamento(@PathParam("id") Long id) {
        boolean deletado = agendamentoResource.deletarPorId(id);
        if (!deletado) {
            return Response.status(Response.Status.NOT_FOUND).build(); // Não encontrado
        }
        return Response.status(Response.Status.NO_CONTENT).build(); // Sucesso, sem conteúdo
    }
}
