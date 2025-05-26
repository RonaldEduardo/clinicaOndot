package org.clinicaOndot.paciente;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PacienteRequestDto {
    //
    public String nomeCompleto;
    //
    public String documento;
    //
    public boolean ativo;

    // Construtor vazio, necessário para a desserialização do JSON
    public PacienteRequestDto() {
    }
}
