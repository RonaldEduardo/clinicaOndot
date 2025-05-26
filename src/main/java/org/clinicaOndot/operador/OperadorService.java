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
        return operadorResource.criar(request);
    }

    @GET
    public List<Operador> listarOperadores() {
        return operadorResource.listar();
    }

    @GET
    @Path("/{id}")
    public Response listarOperador(@PathParam("id") Long id) {
        return operadorResource.listarPorId(id);
    }

    @PUT
    @Path("/{id}")
    public Response atualizarOperador(@PathParam("id") Long id, OperadorRequestDto request) {
        return operadorResource.atualizarPorId(id, request);
    }

    @DELETE // HTTP DELETE para remover um recurso
    @Path("/{id}")
    public Response deletarOperador(@PathParam("id") Long id) {
        return operadorResource.deletarPorId(id);
    }
}
