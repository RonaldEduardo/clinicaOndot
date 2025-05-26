// src/main/java/org/clinicaOndot/model/Pessoa.java (SUGESTÃO DE MUDANÇA)
package org.clinicaOndot.common;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@MappedSuperclass
@Getter
@Setter
@ToString
public abstract class Pessoa{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_completo", length = 100, nullable = false)
    @NotBlank(message = "O nome de usuário é obrigatório.")
    @Size(min = 4, message = "O nome de usuário deve ter no minimo 4 letras")
    private String nomeCompleto;

    @NotBlank(message = "O documento é obrigatorio")
    @Size(max = 14, message = "O documento deve conter 14 caracteres")
    @Pattern(regexp = "\\S+", message = "O documento não pode conter espaços.") // Exemplo de regex genérica
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