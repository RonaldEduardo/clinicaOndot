package org.clinicaOndot.agendamento;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import  jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.clinicaOndot.operador.Operador;
import org.clinicaOndot.statusAgendamento.StatusAgendamento;
import org.clinicaOndot.paciente.Paciente;

import java.time.LocalDateTime;
@Entity
@Table(name = "agendamentos")
@ToString

@Getter
@Setter
public class Agendamento extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // Muitos agendamentos podem ter um paciente (Many agendamentos TO One paciente)
    @JoinColumn(name = "paciente_id", nullable = false) // Define a coluna da chave estrangeira no DB
    private Paciente paciente; // Agendamento tem um objeto Paciente

    @ManyToOne
    @JoinColumn(name = "operador_id") // Ou false, dependendo da regra de negócio
    @Getter
    @Setter
    private Operador operador;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    @Column(name = "observacoes")
    private String observacoes;

    @ManyToOne
    @JoinColumn(name = "status_agendamento_id", nullable = false)
    private StatusAgendamento status;

    // Construtor vazio
    public Agendamento() {
    }

    // Construtor com parâmetros (agora recebendo um Paciente)
    public Agendamento(Paciente paciente, StatusAgendamento status, LocalDateTime dataHora, String observacoes) {
        this.paciente = paciente;
        this.status = status;
        this.dataHora = dataHora;
        this.observacoes = observacoes;
    }
}
