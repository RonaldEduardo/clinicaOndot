package org.clinicaOndot.paciente;

import jakarta.enterprise.context.RequestScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.clinicaOndot.agendamento.Agendamento;

import java.util.List;
import java.util.Optional;

@RequestScoped
public class PacienteResource {
    @Transactional
    public Response criar(PacienteRequestDto request) {
        Paciente paciente = new Paciente();
        paciente.setAtivo(true);
        paciente.setNomeCompleto(request.getNomeCompleto());
        paciente.setDocumento(request.getDocumento());

        paciente.persist();

        return Response.status(Response.Status.CREATED).build();
    }

    public List<Paciente> listarTodos() {
        return Paciente.listAll();
    }

    public Response listarPorId(Long id) {
        Optional<Paciente> paciente = Paciente.findByIdOptional(id);

        if (paciente.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(paciente.get()).build();
    }

    @Transactional
    public Response atualizarPorId(Long id, PacienteRequestDto request) {
        Paciente pacienteExistente = Paciente.findById(id);

        if (pacienteExistente == null) {
            // Se não encontrou, retorna 404 Not Found
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        validaRequest(request, pacienteExistente);

        // Não precisa de persist() aqui pois o Panache gerencia dentro da transação
        return Response.ok(pacienteExistente).build();
    }

    private static void validaRequest(PacienteRequestDto request, Paciente pacienteExistente) {
        if(request.getNomeCompleto() != null){
            pacienteExistente.setNomeCompleto(request.getNomeCompleto());
        }

        if(request.getDocumento() != null){
            pacienteExistente.setDocumento(request.getDocumento());
        }
    }

    @Transactional
    public Response deletarPorId(Long id) {
        Paciente paciente = Paciente.findById(id);

        if (paciente == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        long agendamentosVinculados = Agendamento.count("paciente", paciente);

        if (agendamentosVinculados > 0) {
            // Se houver agendamentos, desativar o paciente (soft delete)
            paciente.setAtivo(false); // Altera o status para inativo
            // O Hibernate salva automaticamente a mudança porque estamos em uma transação
            return Response.ok("Paciente desativado. Existem " + agendamentosVinculados + " agendamento(s) vinculado(s).").build();
        }
        // Se não houver agendamentos, realizar a exclusão física
        paciente.delete(); // Exclusão física usando Panache
        return Response.noContent().build(); // Retorna 204 No Content para sucesso de exclusão
    }


}
