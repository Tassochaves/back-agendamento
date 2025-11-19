-- cria a tabela principal para armazenar agendamentos
CREATE TABLE tb_agendamento(
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(120) NOT NULL,
    descricao TEXT,
    data_inicio TIMESTAMP NOT NULL,
    data_fim TIMESTAMP NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'AGENDADO',
    usuario VARCHAR(80) NOT NULL,
    criado_em TIMESTAMP NOT NULL DEFAULT NOW(),
    atualizado_em TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT ck_status CHECK (status IN ('AGENDADO', 'CANCELADO', 'CONCLUIDO')),
    CONSTRAINT ck_intervalo CHECK (data_inicio < data_fim)
);

-- cria um índice composto para melhorar a performance de busca.
CREATE INDEX idx_ag_usuario_inicio_fim
    ON tb_agendamento (usuario, data_inicio, data_fim);

-- define o valor de 'atualizado_em' para o timestamp atual
CREATE OR REPLACE FUNCTION set_atualizado_em()
RETURN TRIGGER AS $$
BEGIN
    NEW.atualizado_em := NOW();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- c um TRIGGER associado a tb_agendamento
CREATE TRIGGER trg_set_atualizado_em
BEFORE UPDATE ON tb_agendamento
FOR EACH ROW
EXECUTE FUNCTION set_atualizado_em();