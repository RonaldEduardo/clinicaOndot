-- src/main/resources/db/migrations/V1.0.3__add_ativo_to_pacientes.sql
ALTER TABLE pacientes
ADD COLUMN ativo BOOLEAN NOT NULL DEFAULT TRUE;