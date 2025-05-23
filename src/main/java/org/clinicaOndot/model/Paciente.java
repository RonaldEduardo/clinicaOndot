package org.clinicaOndot.model; // OU seu pacote

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "pacientes")

@ToString
@Getter
@Setter
public class Paciente extends Pessoa {
    @Column(nullable = false) // Novo campo para indicar se o paciente está ativo
    private boolean ativo; // true = ativo, false = inativo

    public Paciente() {
        super();
    }

    // Construtor com parâmetros
    public Paciente(String nomeCompleto, String documento) {
        super(nomeCompleto, documento);
        this.ativo = true;
    }
}