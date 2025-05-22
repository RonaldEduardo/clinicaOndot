CREATE TABLE agendamentos (
    id BIGSERIAL PRIMARY KEY, -- BIGSERIAL é um tipo do PostgreSQL que cria um ID automático e sequencial
    id_paciente_fk INT,
    data_hora TIMESTAMP WITHOUT TIME ZONE NOT NULL, -- TIMESTAMP para data e hora
    observacoes VARCHAR(255)
);
-- Definindo a Chave Estrangeira (Foreign Key)
    CONSTRAINT fk_agendamento_paciente
        FOREIGN KEY (id_paciente_fk)
        REFERENCES pacientes(id)