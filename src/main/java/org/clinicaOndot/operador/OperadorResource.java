package org.clinicaOndot.operador;

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
public class OperadorResource {
    @POST
    @Transactional
    public Response criarOperador(Operador operador) {
        operador.persist();

        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    public List<Operador> listarOperadores() {
        return Operador.listAll();
    }

    @GET
    @Path("/{id}")
    public Response listarOperador(@PathParam("id") Long id) {
        Optional<Operador> operador = Operador.findByIdOptional(id);

        if (operador.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(operador.get()).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response atualizarOperador(@PathParam("id") Long id, Operador operadorAtualizado) {
        Operador operadorExistente = Operador.findById(id);

        if (operadorExistente == null) {
            // Se não encontrou, retorna 404 Not Found
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        operadorExistente.setNomeCompleto(operadorAtualizado.getNomeCompleto());
        operadorExistente.setDocumento(operadorAtualizado.getDocumento());
        // Não precisa de persist() aqui pois o Panache gerencia dentro da transação
        return Response.ok(operadorExistente).build();
    }

    @DELETE // HTTP DELETE para remover um recurso
    @Path("/{id}")
    @Transactional
    public Response deletarOperador(@PathParam("id") Long id) {
        Operador operador = Operador.findById(id);

        if (operador == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        long agendamentosVinculados = Agendamento.count("operador", operador);

        if (agendamentosVinculados > 0) {
            operador.setAtivo(false); // Altera o status para inativo
            // O Hibernate salva automaticamente a mudança porque estamos em uma transação
            return Response.ok("Operador desativado. Existem " + agendamentosVinculados + " agendamento(s) vinculado(s).")
                    .build();
        }
        // Se não houver agendamentos, realizar a exclusão física
        operador.delete(); // Exclusão física usando Panache
        return Response.noContent().build(); // Retorna 204 No Content para sucesso de exclusão
    }
}
