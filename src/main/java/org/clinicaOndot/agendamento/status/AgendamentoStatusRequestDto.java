package org.clinicaOndot.agendamento.status;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgendamentoStatusRequestDto {
    @Column(name = "descricao", nullable = false, length = 20)
    @NotNull
    private String descricao;

    // Construtor vazio
    public AgendamentoStatusRequestDto() {
    }
}
