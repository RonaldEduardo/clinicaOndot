package org.clinicaOndot.operador;

import jakarta.enterprise.context.RequestScoped;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.Response;
import org.clinicaOndot.agendamento.Agendamento;

import java.util.List;
import java.util.Optional;

@RequestScoped
public class OperadorResource {

    public Response criar(@Valid OperadorRequestDto request) {
        Operador operador = new Operador();
        operador.setAtivo(true);
        operador.setNomeCompleto(request.getNomeCompleto());
        operador.setDocumento(request.getDocumento());

        operador.persist();

        return Response.status(Response.Status.CREATED).build();
    }

    public List<Operador> listar() {
        return Operador.listAll();
    }

    public Response listarPorId(Long id) {
        Optional<Operador> operador = Operador.findByIdOptional(id);

        if (operador.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(operador.get()).build();
    }

    @Transactional
    public Response atualizarPorId(Long id, @Valid OperadorRequestDto request) {
        Operador operadorExistente = Operador.findById(id);

        if (operadorExistente == null) {
            // Se não encontrou, retorna 404 Not Found
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        validaRequest(request, operadorExistente);
        // Não precisa de persist() aqui pois o Panache gerencia dentro da transação
        return Response.ok(operadorExistente).build();
    }

    @Transactional
    public Response deletarPorId(Long id) {
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

    private static void validaRequest(OperadorRequestDto request, Operador operadorExistente) {
        if(request.getNomeCompleto() != null){
            operadorExistente.setNomeCompleto(request.getNomeCompleto());
        }

        if(request.getDocumento() != null){
            operadorExistente.setDocumento(request.getDocumento());
        }
    }
}
