CREATE TABLE agendamentos (
    id BIGSERIAL PRIMARY KEY,
    paciente_id BIGINT NOT NULL, -- Use o nome que você usou no Agendamento.java para clareza
    data_hora TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    observacoes VARCHAR(255),
    CONSTRAINT fk_agendamento_paciente
        FOREIGN KEY (paciente_id)
        REFERENCES pacientes(id)
);