package org.clinicaOndot.statusAgendamento;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;


public class StatusAgendamentoResource {

    @Transactional
    public Response criar(@Valid StatusAgendamento novoStatus) {
        novoStatus.persist();

        return Response.status(Response.Status.CREATED).build();
    }

    public List<StatusAgendamento> listar() {
        return StatusAgendamento.listAll();
    }

    public Response listarPorId(Long id) {
        Optional<StatusAgendamento> status = StatusAgendamento.findByIdOptional(id);

        if (status.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(status.get()).build();
    }

    @Transactional
    public Response atualizarPorId(Long id, StatusAgendamento statusAtualizado) {
        StatusAgendamento statusExistente = StatusAgendamento.findById(id);

        if (statusExistente == null) {
            // Se não encontrou, retorna 404 Not Found
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        statusExistente.setDescricao(statusAtualizado.getDescricao());
        // Não precisa de persist() aqui pois o Panache gerencia dentro da transação
        return Response.ok(statusExistente).build();
    }

    @Transactional
    public Response deletarPorId(Long id) {
        StatusAgendamento status = StatusAgendamento.findById(id);

        if (status == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        // Se não houver agendamentos, realizar a exclusão física
        status.delete(); // Exclusão física usando Panache
        return Response.noContent().build(); // Retorna 204 No Content para sucesso de exclusão
    }
}
