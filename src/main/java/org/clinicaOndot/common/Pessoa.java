// src/main/java/org/clinicaOndot/model/Pessoa.java (SUGESTÃO DE MUDANÇA)
package org.clinicaOndot.common;

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

    @Column(nullable = false)
    private boolean ativo = true;

    public Pessoa() {
    }

    public Pessoa(String nomeCompleto, String documento) {
        this.nomeCompleto = nomeCompleto;
        this.documento = documento;
    }
    // Considere adicionar construtor com 'ativo' também se quiser controlar a inicialização
    public Pessoa(String nomeCompleto, String documento, boolean ativo) {
        this.nomeCompleto = nomeCompleto;
        this.documento = documento;
        this.ativo = ativo;
    }
}