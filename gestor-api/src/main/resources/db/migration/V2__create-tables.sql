CREATE TABLE cliente (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    telefone VARCHAR(20),
    tipo_cliente VARCHAR(50),
    observacao TEXT
);
CREATE TABLE trabalho (
    id SERIAL PRIMARY KEY,
    cliente_id BIGINT NOT NULL,
    tipo_trabalho VARCHAR(100),
    faculdade VARCHAR(255),
    curso VARCHAR(255),
    tema TEXT,
    caminho_pendrive TEXT,
    caminho_drive TEXT,
    observacao TEXT,
    status_entregas VARCHAR(50),
    status_parcelas VARCHAR(50),
    tipo_pagamento VARCHAR(50),
    valor_total DECIMAL(10, 2),
    valor_pago DECIMAL(10, 2),
    FOREIGN KEY (cliente_id) REFERENCES cliente(id) ON DELETE CASCADE
);
CREATE TABLE entrega (
    id SERIAL PRIMARY KEY,
    trabalho_id BIGINT NOT NULL,
    nome VARCHAR(255),
    data DATE,
    status VARCHAR(50),
    FOREIGN KEY (trabalho_id) REFERENCES trabalho(id) ON DELETE CASCADE
);
CREATE TABLE parcela (
    id SERIAL PRIMARY KEY,
    trabalho_id BIGINT NOT NULL,
    nome VARCHAR(255),
    valor DECIMAL(10, 2),
    data DATE,
    status VARCHAR(50),
    FOREIGN KEY (trabalho_id) REFERENCES trabalho(id) ON DELETE CASCADE
);
