package org.clinicaOndot.agendamento;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import org.clinicaOndot.agendamento.status.AgendamentoStatus;
import org.clinicaOndot.agendamento.status.AgendamentoStatusRepository;
import org.mockito.Mockito;
import org.clinicaOndot.paciente.Paciente;
import org.clinicaOndot.paciente.PacienteRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

@QuarkusTest
public class AgendamentoResourceTest {
    @Inject
    AgendamentoResource agendamentoResource;

    @InjectMock
    AgendamentoRepository agendamentoRepository;

    @InjectMock
    PacienteRepository pacienteRepository;

    @InjectMock
    AgendamentoStatusRepository agendamentoStatusRepository;

    @Test
   // @DisplayName("╯°□°）╯")
    void testCriarAgendamento_Sucesso(){
        AgendamentoRequestDto request = new AgendamentoRequestDto();
        request.setPacienteId(1L);
        request.setStatusAgendamentoId(1L);
        request.setDataHora(LocalDateTime.now().plusDays(1));
        request.setObservacoes("Consulta de Rotina");

        Paciente pacienteMock = new Paciente();
        pacienteMock.setId(1L);
        pacienteMock.setNomeCompleto("Test");
        pacienteMock.setDocumento("908.890.890-20");

        AgendamentoStatus statusMock = new AgendamentoStatus();
        statusMock.setId(1L);
        statusMock.setDescricao("Confirmado");

        Mockito.when(pacienteRepository.findById(1L)).thenReturn(pacienteMock);
        Mockito.when(agendamentoStatusRepository.findById(1L)).thenReturn(statusMock);

        doNothing().when(agendamentoRepository).persist(any(Agendamento.class));

        Agendamento agendamentoCriado = agendamentoResource.criar(request);

        assertNotNull(agendamentoCriado, "O agendamento nao deve ser null");
        assertEquals(pacienteMock, agendamentoCriado.getPaciente(), "O paciente do agendamento não é o esperado.");
        assertEquals(statusMock, agendamentoCriado.getStatus(), "O status do agendamento não é o esperado.");
        assertEquals(request.getDataHora(), agendamentoCriado.getDataHora(), "A data/hora do agendamento não é a esperada.");
        assertEquals(request.getObservacoes(), agendamentoCriado.getObservacoes(), "As observações do agendamento não são as esperadas.");

        // Verifica se o método persist foi chamado no repositório de agendamento uma vez com qualquer agendamento
        Mockito.verify(agendamentoRepository, Mockito.times(1)).persist(any(Agendamento.class));
        // Verifica se o método findById foi chamado no repositório de paciente uma vez com o ID 1L
        Mockito.verify(pacienteRepository, Mockito.times(1)).findById(1L);
        // Verifica se o método findById foi chamado no repositório de status uma vez com o ID 1L
        Mockito.verify(agendamentoStatusRepository, Mockito.times(1)).findById(1L);

    }
    @Test
    void testCriarAgendamento_PacienteNaoEncontrado() {
        // 1. Arrange
        AgendamentoRequestDto request = new AgendamentoRequestDto();
        request.setPacienteId(99L); // ID de paciente que não existe
        request.setStatusAgendamentoId(1L);
        request.setDataHora(LocalDateTime.now().plusDays(1));

        // Configura o mock do pacienteRepository para retornar null quando o ID 99L for buscado
        Mockito.when(pacienteRepository.findById(99L)).thenReturn(null);

        // 2. Act & 3. Assert
        // Verifica se uma BadRequestException é lançada
        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            agendamentoResource.criar(request);
        });

        // Verifica a mensagem da exceção
        assertEquals("Paciente com ID 99 não encontrado.", exception.getMessage(), "Mensagem da exceção não é a esperada.");

        // Verifica se o método findById foi chamado no pacienteRepository
        Mockito.verify(pacienteRepository, Mockito.times(1)).findById(99L);
        // Garante que o persist não foi chamado no agendamentoRepository
        Mockito.verify(agendamentoRepository, Mockito.never()).persist(any(Agendamento.class));
    }

    @Test
    void testCriarAgendamento_StatusNaoEncontrado() {
        // 1. Arrange
        AgendamentoRequestDto request = new AgendamentoRequestDto();
        request.setPacienteId(1L);
        request.setStatusAgendamentoId(88L); // ID de status que não existe
        request.setDataHora(LocalDateTime.now().plusDays(1));

        Paciente pacienteMock = new Paciente();
        pacienteMock.setId(1L);

        Mockito.when(pacienteRepository.findById(1L)).thenReturn(pacienteMock);
        // Configura o mock do agendamentoStatusRepository para retornar null
        Mockito.when(agendamentoStatusRepository.findById(88L)).thenReturn(null);

        // 2. Act & 3. Assert
        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            agendamentoResource.criar(request);
        });

        assertEquals("Status com ID 88 não encontrado.", exception.getMessage());

        Mockito.verify(pacienteRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(agendamentoStatusRepository, Mockito.times(1)).findById(88L);
        Mockito.verify(agendamentoRepository, Mockito.never()).persist(any(Agendamento.class));
    }
}
