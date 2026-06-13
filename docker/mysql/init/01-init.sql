ALTER DATABASE medalerta
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

USE medalerta;

CREATE TABLE usuario (
    id_usuario int auto_increment primary key,
    nome varchar(100) not null,
    telefone varchar(20) not null,
    email varchar(100) not null
);

CREATE TABLE medicamento (
    id_medicamento int auto_increment primary key,
    nome_comercial varchar(100) not null,
    nome_generico varchar(100),
    unidade_medida enum('UNIDADE', 'ML'),
    forma_uso varchar(100),
    observacao varchar(200)
);

CREATE TABLE tratamento (
    id_tratamento int auto_increment primary key,
    id_usuario int not null,
    descricao varchar(100),
    data_inicio date,
    data_fim date,
    status varchar(30),
    foreign key (id_usuario) references usuario(id_usuario)
);

CREATE TABLE tratamento_medicamento (
    id_tratamento_medicamento int auto_increment primary key,
    id_tratamento int not null,
    id_medicamento int not null,
    quantidade varchar(50),
    observacao varchar(200),
    horario_uso time,
    frequencia_uso varchar(50),
    unique key uk_tratamento_medicamento (id_tratamento, id_medicamento),
    foreign key (id_tratamento) references tratamento(id_tratamento),
    foreign key (id_medicamento) references medicamento(id_medicamento)
);

CREATE TABLE alerta (
    id_alerta int auto_increment primary key,
    id_tratamento int not null,
    data_horario_alerta datetime not null,
    status_alerta enum('EMITIDO', 'NAO_EMITIDO'),
    data_horario_consumo datetime,
    confirmacao_consumo enum('SIM', 'NAO'),
    foreign key (id_tratamento) references tratamento(id_tratamento)
);

INSERT INTO usuario (nome, telefone, email) VALUES
('Ana Souza', '41999990001', 'ana@email.com'),
('Bruno Lima', '41999990002', 'bruno@email.com'),
('Carla Mendes', '41999990003', 'carla@email.com'),
('Daniel Rocha', '41999990004', 'daniel@email.com'),
('Elisa Torres', '41999990005', 'elisa@email.com'),
('Felipe Alves', '41999990006', 'felipe@email.com'),
('Gabriela Nunes', '41999990007', 'gabriela@email.com'),
('Henrique Pires', '41999990008', 'henrique@email.com'),
('Isabela Costa', '41999990009', 'isa@email.com'),
('Joao Martins', '41999990010', 'joao@email.com');

INSERT INTO medicamento (nome_comercial, nome_generico, unidade_medida, forma_uso, observacao) VALUES
('Tylenol', 'Paracetamol', 'UNIDADE', 'oral', 'dor e febre'),
('Advil', 'Ibuprofeno', 'UNIDADE', 'oral', 'anti-inflamatorio'),
('Amoxil', 'Amoxicilina', 'ML', 'oral', 'antibiotico'),
('Dipirona', 'Dipirona Sodica', 'UNIDADE', 'oral', null),
('Buscopan', 'Butilbrometo', 'UNIDADE', 'oral', 'colicas'),
('Novalgina', 'Dipirona', 'ML', 'oral', null),
('Atenol', 'Atenolol', 'UNIDADE', 'oral', 'pressao'),
('Losartana', 'Losartana', 'UNIDADE', 'oral', null),
('Vick', 'Camphora', 'ML', 'topico', 'nao ingerir'),
('Cataflam', 'Diclofenaco', 'UNIDADE', 'oral', 'dor muscular');

INSERT INTO tratamento (id_usuario, descricao, data_inicio, data_fim, status) VALUES
(1, 'Controle de dor e febre', '2026-04-01', null, 'ATIVO'),
(2, 'Tratamento anti-inflamatorio', '2026-04-02', '2026-04-12', 'ATIVO'),
(3, 'Antibiotico pos-consulta', '2026-04-03', '2026-04-10', 'ATIVO'),
(4, 'Controle de colicas', '2026-04-04', null, 'ATIVO'),
(5, 'Controle de pressao', '2026-04-05', null, 'ATIVO'),
(6, 'Tratamento topico', '2026-04-06', null, 'ATIVO'),
(7, 'Controle de dor muscular', '2026-04-07', null, 'ATIVO'),
(8, 'Tratamento diario', '2026-04-08', null, 'ATIVO'),
(9, 'Tratamento de suporte', '2026-04-09', null, 'ATIVO'),
(10, 'Rotina medicamentosa', '2026-04-10', null, 'ATIVO');

INSERT INTO tratamento_medicamento
(id_tratamento, id_medicamento, quantidade, observacao, horario_uso, frequencia_uso) VALUES
(1, 1, '1 comprimido', null, '08:00:00', '8h'),
(2, 2, '1 comprimido', null, '09:00:00', '12h'),
(3, 3, '10 ml', null, '10:00:00', '1x dia'),
(4, 5, '1 comprimido', null, '11:00:00', '1x dia'),
(5, 7, '1 comprimido', 'tomar apos cafe', '08:30:00', '1x dia'),
(5, 8, '1 comprimido', 'tomar a noite', '20:30:00', '1x dia'),
(6, 9, '5 ml', null, '12:00:00', '1x dia'),
(7, 10, '1 comprimido', null, '13:00:00', '12h'),
(8, 4, '1 comprimido', null, '14:00:00', '1x dia'),
(9, 6, '15 ml', null, '15:00:00', '1x dia'),
(10, 1, '1 comprimido', null, '07:30:00', '1x dia');

INSERT INTO alerta (id_tratamento, data_horario_alerta, status_alerta, data_horario_consumo, confirmacao_consumo) VALUES
(1, '2026-04-15 07:55:00', 'EMITIDO', '2026-04-15 08:05:00', 'SIM'),
(2, '2026-04-15 08:55:00', 'EMITIDO', '2026-04-15 09:10:00', 'SIM'),
(3, '2026-04-15 09:55:00', 'NAO_EMITIDO', null, 'NAO'),
(4, '2026-04-15 10:55:00', 'EMITIDO', '2026-04-15 11:05:00', 'SIM'),
(5, '2026-04-15 08:25:00', 'EMITIDO', '2026-04-15 08:35:00', 'SIM'),
(6, '2026-04-15 11:55:00', 'NAO_EMITIDO', null, 'NAO'),
(7, '2026-04-15 12:55:00', 'EMITIDO', '2026-04-15 13:10:00', 'SIM'),
(8, '2026-04-15 13:55:00', 'EMITIDO', '2026-04-15 14:05:00', 'SIM'),
(9, '2026-04-15 14:55:00', 'EMITIDO', '2026-04-15 15:10:00', 'SIM'),
(10, '2026-04-15 07:25:00', 'NAO_EMITIDO', null, 'NAO');
