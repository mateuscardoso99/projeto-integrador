ALTER TABLE partida ADD COLUMN encerrado BOOLEAN NOT NULL DEFAULT FALSE;
UPDATE partida SET encerrado = TRUE;