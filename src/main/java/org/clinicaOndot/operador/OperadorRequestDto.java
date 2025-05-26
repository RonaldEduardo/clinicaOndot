package org.clinicaOndot.operador;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class OperadorRequestDto {
    //
    public String nomeCompleto;
    //
    public String documento;
    //
    public boolean ativo;

    // Construtor vazio, necessário para a desserialização do JSON
    public OperadorRequestDto() {
    }
}
