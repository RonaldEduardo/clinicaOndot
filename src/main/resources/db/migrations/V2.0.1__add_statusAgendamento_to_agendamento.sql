ALTER TABLE agendamentos (
ADD COLUMN stataus_agendamento_id BIGSERIAL,
    CONSTRAINT fk_agendamento_statys
        FOREIGN KEY (status_agendamento_id)
        REFERENCES stausagendamento(id)
);