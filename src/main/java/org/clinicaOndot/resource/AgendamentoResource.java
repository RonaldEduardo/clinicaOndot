package org.clinicaOndot.resource;

import org.clinicaOndot.model.Agendamento; // Importe a entidade que criamos
import jakarta.transaction.Transactional; // Importe para garantir operações de DB dentro de uma transação
import jakarta.ws.rs.*; // Importe as anotações JAX-RS
import jakarta.ws.rs.core.MediaType; // Para definir o tipo de conteúdo (JSON)
import jakarta.ws.rs.core.Response; // Para retornar respostas HTTP

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional; // Para lidar com a possibilidade de não encontrar um agendamento

@Path("/agendamentos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AgendamentoResource {
    @POST
    @Transactional
    public Response criarAgendamento(Agendamento agendamento) {
        agendamento.persist(); // usando o panche para salver os dados no banco
        // Retorna uma resposta 201 Created, com o agendamento salvo no corpo da resposta
        return Response.status(Response.Status.CREATED).entity(agendamento).build();
    }

    @GET
    public List<Agendamento> listarAgendamento() {
        return Agendamento.listAll();
    }

    @GET
    @Path("/{id}")
    public Response buscarAgendamentoPorId(@PathParam("id") Long id) {
        Optional<Agendamento> agendamento = Agendamento.findByIdOptional(id);

        if (agendamento.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(agendamento.get()).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response atualizarAgendamento(@PathParam("id") Long id) {
        Agendamento agendamentoExistente = Agendamento.findById(id);

        if (agendamentoExistente == null) {
            // Se não encontrou, retorna 404 Not Found
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        // Cria logica para alteracao
        // Retorna 200 OK com o agendamento atualizado
        return Response.ok(agendamentoExistente).build();
    }

    @DELETE // HTTP DELETE para remover um recurso
    @Path("/{id}")
    @Transactional
    public Response deletarAgendamento(@PathParam("id") Long id) {
        boolean deleted = Agendamento.deleteById(id);

        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.noContent().build();
    }
}
