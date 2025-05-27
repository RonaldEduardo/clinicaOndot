package org.clinicaOndot.operador;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.Response;
import org.clinicaOndot.agendamento.Agendamento;
import org.clinicaOndot.agendamento.AgendamentoRequestDto;
import org.clinicaOndot.agendamento.status.AgendamentoStatus;

import java.util.List;
import java.util.Optional;

@RequestScoped
public class OperadorResource {
    @Inject
    OperadorRepository operadorRepository;

    public Operador criar(@Valid OperadorRequestDto request) {
        Operador operador = new Operador();
        operador.setAtivo(true);
        operador.setNomeCompleto(request.getNomeCompleto());
        operador.setDocumento(request.getDocumento());

        operadorRepository.persist(operador);

        return operador;
    }

    public List<Operador> listar() {
        return operadorRepository.listAll();
    }

    public Optional<Operador> listarPorId(Long id) {
        return operadorRepository.findByIdOptional(id);
    }

    @Transactional
    public Optional<Operador> atualizarPorId(Long id, @Valid OperadorRequestDto operadorRequestDto) {
        Optional<Operador> operadorExistenteOpt = operadorRepository.findByIdOptional(id);

        if (operadorExistenteOpt.isEmpty()) {
            // Se não encontrou, retorna false
            return Optional.empty();
        }
        Operador operadorExistente = operadorExistenteOpt.get();
        atualizaDados(operadorRequestDto, operadorExistente);

        return Optional.of(operadorExistente);
    }

    private static void atualizaDados(OperadorRequestDto request, Operador operadorExistente) {
        if (request.getNomeCompleto() != null) { // Adicionamos esta verificação!
            operadorExistente.setNomeCompleto(request.getNomeCompleto());
        }
        if (request.getDocumento() != null) { // Opcional: fazer o mesmo para observacoes se ela também puder ser nula na requisição
            operadorExistente.setDocumento(request.getDocumento());
        }
    }

    @Transactional
    public boolean deletarPorId(Long id) {
        return operadorRepository.deleteById(id);
    }
}
