package org.clinicaOndot.model; // OU seu pacote
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "pacientes")
@ToString
public class Paciente extends PanacheEntityBase{
    @Id // Marca o campo 'id' como a chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // O banco de dados vai gerar o ID automaticamente
    @Getter
    @Setter
    private Long id;

    // Mude esta linha para incluir length e nullable!
    @Column(name = "nome_completo", length = 100, nullable = false) // <--- CORREÇÃO AQUI
    @Getter
    @Setter
    private String nomeCompleto;

    @Column(unique = true, length = 14)
    @Getter
    @Setter
    private String documento; // Ex: CPF ou outro identificador único

    public Paciente() {
    }

    // Construtor com parâmetros
    public Paciente(String nomeCompleto, String documento) {
        this.nomeCompleto = nomeCompleto;
        this.documento = documento;
    }
}