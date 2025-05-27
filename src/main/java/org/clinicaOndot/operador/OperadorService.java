package org.clinicaOndot.operador;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.clinicaOndot.agendamento.Agendamento;

import java.util.List;
import java.util.Optional;

@Path("/operadores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OperadorService {
    @Inject
    OperadorResource operadorResource;

    @POST
    @Transactional
    public Response criarOperador(OperadorRequestDto request) {
        operadorResource.criar(request);

        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    public List<Operador> listarOperadores() {
        return operadorResource.listar();
    }

    @GET
    @Path("/{id}")
    public Response listarOperador(@PathParam("id") Long id) {
        Optional<Operador> operador = operadorResource.listarPorId(id);
        return Response.ok(operador.get()).build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizarOperador(@PathParam("id") Long id, OperadorRequestDto request) {
        return operadorResource.atualizarPorId(id, request)
                .map(operador -> Response.ok(operador).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @DELETE // HTTP DELETE para remover um recurso
    @Path("/{id}")
    public Response deletarOperador(@PathParam("id") Long id) {
        boolean deletado = operadorResource.deletarPorId(id);
        if (!deletado) {
            return Response.status(Response.Status.NOT_FOUND).build(); // Não encontrado
        }
        return Response.status(Response.Status.NO_CONTENT).build(); // Sucesso, sem conteúdo
    }
}
