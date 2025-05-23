-- src/main/resources/db/migrations/V1.0.3__add_ativo_to_pacientes.sql
ALTER TABLE agendamentos
ADD COLUMN operador_id BIGINT,
ADD CONSTRAINT fk_agendamento_operador_id
    FOREIGN KEY (operador_id)
    REFERENCES operadores(id);