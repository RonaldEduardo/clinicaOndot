package org.clinicaOndot.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "operadores")

@ToString
@Getter
@Setter
@AttributeOverrides({
        @AttributeOverride( // Para sobrescrever 'documento'
                name = "documento",
                column = @Column(name = "cro", unique = true)
        )
})
public class Operador extends Pessoa {
    @Column(nullable = false) // Novo campo para indicar se o paciente está ativo
    private boolean ativo; // true = ativo, false = inativo

    // Construtor vazio
    public Operador() {
        super();
    }

    public Operador(String nomeCompleto, String documento, boolean ativo) {
        super(nomeCompleto, documento, ativo);
    }
}
