ALTER TABLE cliente
ADD COLUMN indicado_por BIGINT,
ADD CONSTRAINT fk_indicado_por FOREIGN KEY (indicado_por) REFERENCES cliente(id) ON DELETE SET NULL;
