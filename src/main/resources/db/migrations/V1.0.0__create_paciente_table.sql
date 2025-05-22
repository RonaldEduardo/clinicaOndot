CREATE TABLE pacientes (
    id BIGSERIAL PRIMARY KEY,
    nome_completo VARCHAR(100) NOT NULL,
    documento VARCHAR(14) UNIQUE
);