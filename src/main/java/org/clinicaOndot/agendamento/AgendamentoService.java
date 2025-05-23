package org.clinicaOndot.agendamento;

import jakarta.inject.Inject; // Para injetar a camada de negócio
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.clinicaOndot.dto.AgendamentoRequestDto;
import org.clinicaOndot.paciente.Paciente;

import java.util.List;

public class AgendamentoService {
    @Inject // O CDI injeta uma instância da camada de negócio aqui
    AgendamentoResource agendamentoResource;

    @POST
    @Transactional
    public Response criarAgendamento(AgendamentoRequestDto request) {

    }

    @GET
    public List<Agendamento> listarAgendamento() {
    }

    @GET
    @Path("/{id}")
    public Response buscarAgendamentoPorId(@PathParam("id") Long id) {
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response atualizarAgendamento(@PathParam("id") Long id, AgendamentoRequestDto request) {

    }

    @DELETE // HTTP DELETE para remover um recurso
    @Path("/{id}")
    @Transactional
    public Response deletarAgendamento(@PathParam("id") Long id) {

    }
}
