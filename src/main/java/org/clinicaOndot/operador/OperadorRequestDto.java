package org.clinicaOndot.operador;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OperadorRequestDto {
    @NotBlank
    public String nomeCompleto;

    @NotBlank
    @Pattern(regexp = "\\S+") // Exemplo de regex genérica
    public String documento;
    //
    public boolean ativo;

    // Construtor vazio, necessário para a desserialização do JSON
    public OperadorRequestDto() {
    }
}
