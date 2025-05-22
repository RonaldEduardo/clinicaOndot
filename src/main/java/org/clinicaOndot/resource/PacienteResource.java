package org.clinicaOndot.resource;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.clinicaOndot.model.Agendamento;
import org.clinicaOndot.model.Paciente;

import java.util.List;
import java.util.Optional;
@Path("/pacientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PacienteResource {
    @POST
    @Transactional
    public Response criarPaciente(Paciente paciente) {
        paciente.persist();

        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    public List<Paciente> listarPacientes() {
        return Paciente.listAll();
    }

    @GET
    @Path("/{id}")
    public Response listarPaciente(@PathParam("id") Long id) {
        Optional<Paciente> paciente = Paciente.findByIdOptional(id);

        if (paciente.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(paciente.get()).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response atualizarPaciente(@PathParam("id") Long id, Paciente pacienteAtualizado) {
        Paciente pacienteExistente = Paciente.findById(id);

        if (pacienteExistente == null) {
            // Se não encontrou, retorna 404 Not Found
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        pacienteExistente.setNomeCompleto(pacienteAtualizado.getNomeCompleto());
        pacienteExistente.setDocumento(pacienteAtualizado.getDocumento());
        // Não precisa de persist() aqui pois o Panache gerencia dentro da transação
        return Response.ok(pacienteExistente).build();
    }

    @DELETE // HTTP DELETE para remover um recurso
    @Path("/{id}")
    @Transactional
    public Response deletarPaciente(@PathParam("id") Long id) {
        boolean deleted = Agendamento.deleteById(id);

        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.noContent().build();
    }
}
