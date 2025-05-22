package org.clinicaOndot.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import  jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
@Entity
@Table(name = "agendamentos")
@ToString
public class Agendamento extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @ManyToOne // Muitos agendamentos podem ter um paciente (Many agendamentos TO One paciente)
    @JoinColumn(name = "paciente_id", nullable = false) // Define a coluna da chave estrangeira no DB
    @Getter
    @Setter
    private Paciente paciente; // Agendamento tem um objeto Paciente

    @Column(name = "data_hora", nullable = false)
    @Getter
    @Setter
    private LocalDateTime dataHora;

    @Column(length = 255)
    @Getter
    @Setter
    private String observacoes;

    // Construtor vazio
    public Agendamento() {
    }

    // Construtor com parâmetros (agora recebendo um Paciente)
    public Agendamento(Paciente paciente, LocalDateTime dataHora, String observacoes) {
        this.paciente = paciente;
        this.dataHora = dataHora;
        this.observacoes = observacoes;
    }
}
