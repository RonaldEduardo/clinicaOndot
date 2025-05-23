package org.clinicaOndot.paciente; // OU seu pacote

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.clinicaOndot.common.Pessoa;

@Entity
@Table(name = "pacientes")

@ToString
@Getter
@Setter
public class Paciente extends Pessoa {
    public Paciente() {
        super();
    }

    // Construtor com parâmetros
    public Paciente(String nomeCompleto, String documento, boolean ativo) {
        super(nomeCompleto, documento, ativo);
    }
}