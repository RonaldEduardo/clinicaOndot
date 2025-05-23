package org.clinicaOndot.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@MappedSuperclass
@Getter
@Setter
@ToString
public abstract class Pessoa extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_completo", length = 100, nullable = false)
    private String nomeCompleto;

    @Column(unique = true, length = 14)
    private String documento;

    public Pessoa() {
    }

    public Pessoa(String nomeCompleto, String documento) {
        this.nomeCompleto = nomeCompleto;
        this.documento = documento;
    }
}