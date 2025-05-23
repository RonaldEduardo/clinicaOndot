-- src/main/resources/db/migrations/V1.0.3__add_ativo_to_pacientes.sql
ALTER TABLE agendaemtos
ADD COLUMN operador_id BIGINT NOT NULL,
ADD CONSTRAINT fk_agendamento_operador_id
    FOREIGN KEY (operador_id)
    REFERENCES operadores(id);