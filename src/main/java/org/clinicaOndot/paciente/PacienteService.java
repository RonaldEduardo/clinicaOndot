package org.clinicaOndot.paciente;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.clinicaOndot.agendamento.Agendamento;
import org.clinicaOndot.operador.Operador;

import java.util.List;
import java.util.Optional;

@Path("/pacientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PacienteService {
    @Inject
    PacienteResource pacienteResource;


    @POST
    public Response criarPaciente(PacienteRequestDto request) {
        pacienteResource.criar(request);

        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    public List<Paciente> listarPacientes() {
        return  pacienteResource.listarTodos();
    }

    @GET
    @Path("/{id}")
    public Response listarPaciente(@PathParam("id") Long id) {
        Optional<Paciente> paciente = pacienteResource.listarPorId(id);
        return Response.ok(paciente.get()).build();
    }

    @PUT
    @Path("/{id}")
    public  Response atualizarPaciente(@PathParam("id") Long id, PacienteRequestDto request) {
        return pacienteResource.atualizarPorId(id, request)
                .map(paciente -> Response.ok(paciente).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @DELETE // HTTP DELETE para remover um recurso
    @Path("/{id}")
    public Response deletarPaciente(@PathParam("id") Long id) {
        boolean deletado = pacienteResource.deletarPorId(id);
        if (!deletado) {
            return Response.status(Response.Status.NOT_FOUND).build(); // Não encontrado
        }
        return Response.status(Response.Status.NO_CONTENT).build(); // Sucesso, sem conteúdo
    }

}
