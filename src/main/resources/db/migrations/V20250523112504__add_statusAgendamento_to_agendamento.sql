ALTER TABLE agendamentos
ADD COLUMN status_agendamento_id BIGINT,
ADD CONSTRAINT fk_agendamento_status
    FOREIGN KEY (status_agendamento_id)
    REFERENCES statusagendamento(id);