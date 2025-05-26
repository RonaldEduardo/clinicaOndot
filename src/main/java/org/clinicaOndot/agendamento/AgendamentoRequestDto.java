package org.clinicaOndot.agendamento;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class AgendamentoRequestDto {
    // Data e hora do agendamento
    public LocalDateTime dataHora;
    // Observações, que podem ser opcionais
    public String observacoes;
    //
    public Long pacienteId;
    //
    public Long operadorId;
    //
    public Long statusAgendamentoId;


    // Construtor vazio, necessário para a desserialização do JSON
    public AgendamentoRequestDto() {
    }
}
