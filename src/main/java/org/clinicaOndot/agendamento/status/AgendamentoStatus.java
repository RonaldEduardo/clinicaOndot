package org.clinicaOndot.agendamento.status;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "statusagendamento")
public class AgendamentoStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descricao", nullable = false, length = 20)
    @NotNull
    private String descricao;

    // Construtor vazio
    public AgendamentoStatus() {
    }

    public AgendamentoStatus(String descricao) {
        this.descricao = descricao;
    }
}
