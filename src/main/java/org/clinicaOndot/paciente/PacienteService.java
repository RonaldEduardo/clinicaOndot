package org.clinicaOndot.paciente;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/pacientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PacienteService {
    @Inject
    PacienteResource pacienteResource;


    @POST
    public Response criarPaciente(PacienteRequestDto request) {
        return  pacienteResource.criar(request);
    }

    @GET
    public List<Paciente> listarPacientes() {
        return  pacienteResource.listarTodos();
    }

    @GET
    @Path("/{id}")
    public Response listarPaciente(@PathParam("id") Long id) {
        return listarPaciente(id);
    }

    @PUT
    @Path("/{id}")
    public  Response atualizarPaciente(@PathParam("id") Long id, PacienteRequestDto request) {
        return pacienteResource.atualizarPorId(id, request);
    }

    @DELETE // HTTP DELETE para remover um recurso
    @Path("/{id}")
    public Response deletarPaciente(@PathParam("id") Long id) {
        return pacienteResource.deletarPorId(id);
    }

}
