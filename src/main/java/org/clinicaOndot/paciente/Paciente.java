package org.clinicaOndot.paciente; // OU seu pacote

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.clinicaOndot.agendamento.Agendamento;
import org.clinicaOndot.common.Pessoa;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pacientes")

@ToString
@Getter
@Setter
public class Paciente extends Pessoa {
    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Agendamento> agendamentos = new ArrayList<>();
    public Paciente() {
        super();
    }

    // Construtor com parâmetros
    public Paciente(String nomeCompleto, String documento,  boolean ativo) {
        super(nomeCompleto, documento, ativo);
    }
}