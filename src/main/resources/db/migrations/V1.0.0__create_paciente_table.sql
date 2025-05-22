CREATE TABLE pacientes (
    id BIGSERIAL PRIMARY KEY,
    nome_completo VARCHAR(100) NOT NULL, -- Você quer 100 e NOT NULL
    documento VARCHAR(14) UNIQUE
);