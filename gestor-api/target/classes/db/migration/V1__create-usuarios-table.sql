CREATE TABLE usuarios (
    id UUID PRIMARY KEY,
    nome VARCHAR(255),
    email VARCHAR(255),
    senha VARCHAR(255),
    tipo VARCHAR(50) CHECK (tipo IN ('ADMIN', 'USER'))
);