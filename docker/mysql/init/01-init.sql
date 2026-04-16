
CREATE DATABASE IF NOT EXISTS medalerta
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_0900_ai_ci;

USE medalerta;

CREATE TABLE IF NOT EXISTS Usuario (
    idUsuario        INT          NOT NULL AUTO_INCREMENT,
    nome             VARCHAR(100) NOT NULL,
    telefone         VARCHAR(20)  NOT NULL,
    email            VARCHAR(100) NOT NULL,
    enderecoRua      VARCHAR(100),
    enderecoNumero   INT,
    enderecoComplemento VARCHAR(50),
    enderecoBairro   VARCHAR(50),
    enderecoCEP      VARCHAR(10),
    enderecoCidade   VARCHAR(50),
    enderecoEstado   CHAR(2),
    PRIMARY KEY (idUsuario)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS Medicamento (
    idMedicamento  INT          NOT NULL AUTO_INCREMENT,
    nomeComercial  VARCHAR(100) NOT NULL,
    nomeGenerico   VARCHAR(100),
    quantidade     ENUM('unidade', 'ml'),
    formaUso       VARCHAR(100),
    observacao     VARCHAR(200),
    PRIMARY KEY (idMedicamento)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS UsuarioMedicamento (
    idUsuario             INT          NOT NULL,
    idMedicamento         INT          NOT NULL,
    horarioUso            TIME         NOT NULL,
    frequenciaUso         VARCHAR(50),
    dosagem               VARCHAR(50)  NOT NULL,
    dataHorarioAlerta     DATETIME     NOT NULL,
    statusAlerta          ENUM('emitido', 'nao_emitido') NOT NULL,
    dataHorarioConsumo    DATETIME,
    confirmacaoConsumo    ENUM('sim', 'nao') NOT NULL,
    PRIMARY KEY (idUsuario, idMedicamento),
    CONSTRAINT fk_um_usuario     FOREIGN KEY (idUsuario)     REFERENCES Usuario(idUsuario),
    CONSTRAINT fk_um_medicamento FOREIGN KEY (idMedicamento) REFERENCES Medicamento(idMedicamento)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT IGNORE INTO Usuario (nome, telefone, email, enderecoRua, enderecoNumero, enderecoCidade, enderecoEstado)
VALUES
  ('João Silva',  '41999990001', 'joao@email.com',  'Rua das Flores', 100, 'Curitiba', 'PR'),
  ('Maria Souza', '41999990002', 'maria@email.com', 'Av. Brasil',      200, 'Curitiba', 'PR');

INSERT IGNORE INTO Medicamento (nomeComercial, nomeGenerico, quantidade, formaUso, observacao)
VALUES
  ('Losartana 50mg',  'Losartana Potássica', 'unidade', 'Via oral', 'Tomar com água'),
  ('Metformina 850mg','Metformina',          'unidade', 'Via oral', 'Tomar após refeição');