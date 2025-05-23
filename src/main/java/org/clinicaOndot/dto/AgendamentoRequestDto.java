package org.clinicaOndot.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class AgendamentoRequestDto {
    // Queremos receber apenas o ID do paciente, não o objeto Paciente completo
    public Long pacienteId;

    // Data e hora do agendamento
    public LocalDateTime dataHora;

    // Observações, que podem ser opcionais
    public String observacoes;

    public Long statusAgendamentoId;

    // Construtor vazio, necessário para a desserialização do JSON
    public AgendamentoRequestDto() {
    }
}
