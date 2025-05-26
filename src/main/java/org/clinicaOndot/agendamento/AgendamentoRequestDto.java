package org.clinicaOndot.agendamento;


import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class AgendamentoRequestDto {
    @NotNull
    @Future
    public LocalDateTime dataHora;
    public String observacoes;
    @NotNull
    public Long pacienteId;
    @NotNull
    public Long operadorId;
    @NotNull
    public Long statusAgendamentoId;


    // Construtor vazio, necessário para a desserialização do JSON
    public AgendamentoRequestDto() {
    }
}
