package org.clinicaOndot.statusAgendamento;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "statusagendamento")
public class StatusAgendamento extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descricao", nullable = false, length = 20)
    private String descricao;

    // Construtor vazio
    public StatusAgendamento() {
    }

    public StatusAgendamento(String descricao) {
        this.descricao = descricao;
    }
}
