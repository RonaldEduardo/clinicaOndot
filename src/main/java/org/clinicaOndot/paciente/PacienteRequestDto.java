package org.clinicaOndot.paciente;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PacienteRequestDto {
    @NotBlank(message = "O nome de usuário é obrigatório.")
    @Size(min = 4, message = "O nome de usuário deve ter no minimo 4 letras")
    public String nomeCompleto;

    @NotBlank(message = "O documento é obigatorio")
    @Size(max = 14, message = "O documento deve conter 14 caracteres")
    @Pattern(regexp = "\\S+", message = "O documento não pode conter espaços.") // Exemplo de regex genérica
    public String documento;

    public boolean ativo;

    // Construtor vazio, necessário para a desserialização do JSON
    public PacienteRequestDto() {
    }
}
