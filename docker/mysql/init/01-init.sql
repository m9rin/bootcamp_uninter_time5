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

CREATE TABLE endereco (
    id_endereco int auto_increment primary key,
    id_usuario int not null,
    rua varchar(100),
    numero int,
    complemento varchar(50),
    bairro varchar(50),
    cep varchar(10),
    cidade varchar(50),
    estado char(2),
    foreign key (id_usuario) references usuario(id_usuario)
);

CREATE TABLE medicamento (
    id_medicamento int auto_increment primary key,
    nome_comercial varchar(100) not null,
    nome_generico varchar(100),
    quantidade enum('UNIDADE', 'ML'),
    forma_uso varchar(100),
    observacao varchar(200)
);

CREATE TABLE tratamento (
    id_tratamento int auto_increment primary key,
    id_usuario int not null,
    horario_uso time,
    frequencia_uso varchar(50),
    foreign key (id_usuario) references usuario(id_usuario)
);

CREATE TABLE tratamento_medicamento (
    idTratamento int not null,
    idMedicamento int not null,
    quantidade varchar(50),
    observacao varchar(200),
    primary key (id_tratamento, id_medicamento),
    foreign key (id_tratamento) references tratamento(id_tratamento),
    foreign key (id_medicamento) references medicamento(id_medicamento)
);

CREATE TABLE alerta (
    id_alerta int auto_increment primary key,
    id_tratamento int not null,
    data_horario_alerta datetime,
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
('João Martins', '41999990010', 'joao@email.com'),
('Lucas Ferreira', '41999990011', 'lucas@email.com'),
('Mariana Castro', '41999990012', 'mari@email.com'),
('Rafael Gomes', '41999990013', 'rafael@email.com'),
('Patricia Ribeiro', '41999990014', 'patricia@email.com'),
('Eduardo Santana', '41999990015', 'eduardo@email.com');

INSERT INTO endereco (id_usuario, rua, numero, bairro, cep, cidade, estado) VALUES
(1,'Rua A',10,'Centro','80000-001','Curitiba','PR'),
(2,'Rua B',20,'Batel','80000-002','Curitiba','PR'),
(3,'Rua C',30,'Água Verde','80000-003','Curitiba','PR'),
(4,'Rua D',40,'Portão','80000-004','Curitiba','PR'),
(5,'Rua E',50,'Cabral','80000-005','Curitiba','PR'),
(6,'Rua F',60,'Xaxim','80000-006','Curitiba','PR'),
(7,'Rua G',70,'Boqueirão','80000-007','Curitiba','PR'),
(8,'Rua H',80,'Rebouças','80000-008','Curitiba','PR'),
(9,'Rua I',90,'Centro','80000-009','Curitiba','PR'),
(10,'Rua J',100,'Hauer','80000-010','Curitiba','PR'),
(11,'Rua K',110,'Bigorrilho','80000-011','Curitiba','PR'),
(12,'Rua L',120,'Juvevê','80000-012','Curitiba','PR'),
(13,'Rua M',130,'Alto da XV','80000-013','Curitiba','PR'),
(14,'Rua N',140,'Mercês','80000-014','Curitiba','PR'),
(15,'Rua O',150,'Centro Cívico','80000-015','Curitiba','PR');

INSERT INTO medicamento (nome_comercial, nome_generico, quantidade, forma_uso, observacao) VALUES
('Tylenol','Paracetamol','UNIDADE','oral','dor e febre'),
('Advil','Ibuprofeno','UNIDADE','oral','anti-inflamatório'),
('Amoxil','Amoxicilina','ML','oral','antibiótico'),
('Dipirona','Dipirona Sódica','UNIDADE','oral',null),
('Buscopan','Butilbrometo','UNIDADE','oral','cólicas'),
('Novalgina','Dipirona','ML','oral',null),
('Atenol','Atenolol','UNIDADE','oral','pressão'),
('Losartana','Losartana','UNIDADE','oral',null),
('Vick','Camphora','ML','tópico','não ingerir'),
('Cataflam','Diclofenaco','UNIDADE','oral','dor muscular');

INSERT INTO tratamento (id_usuario, horario_uso, frequencia_uso) VALUES
(1,'08:00:00','8h'), (2,'09:00:00','12h'),
(3,'10:00:00','1x dia'), (4,'11:00:00','1x dia'),
(5,'08:30:00','8h'), (6,'12:00:00','1x dia'),
(7,'13:00:00','12h'), (8,'14:00:00','1x dia'),
(9,'15:00:00','1x dia'), (10,'16:00:00','1x dia'),
(11,'08:00:00','1x dia'), (12,'09:30:00','1x dia'),
(13,'10:30:00','8h'), (14,'11:30:00','12h'),
(15,'07:30:00','1x dia');

INSERT INTO tratamento_medicamento VALUES
(1,1,'1 comprimido',null),
(2,2,'1 comprimido',null),
(3,3,'10 ml',null),
(4,4,'1 comprimido',null),
(5,5,'1 comprimido',null),
(6,6,'15 ml',null),
(7,7,'1 comprimido',null),
(8,8,'1 comprimido',null),
(9,9,'5 ml',null),
(10,10,'1 comprimido',null),
(11,1,'1 comprimido',null),
(12,2,'1 comprimido',null),
(13,3,'10 ml',null),
(14,4,'1 comprimido',null),
(15,5,'1 comprimido',null);

INSERT INTO alerta (id_tratamento, data_horario_alerta, status_alerta, data_horario_consumo, confirmacao_consumo) VALUES
(1,'2026-04-15 07:55:00','EMITIDO','2026-04-15 08:05:00','SIM'),
(2,'2026-04-15 08:55:00','EMITIDO','2026-04-15 09:10:00','SIM'),
(3,'2026-04-15 09:55:00','NAO_EMITIDO',null,'NAO'),
(4,'2026-04-15 10:55:00','EMITIDO','2026-04-15 11:05:00','SIM'),
(5,'2026-04-15 07:25:00','EMITIDO','2026-04-15 08:00:00','SIM'),
(6,'2026-04-15 11:55:00','NAO_EMITIDO',null,'NAO'),
(7,'2026-04-15 12:55:00','EMITIDO','2026-04-15 13:10:00','SIM'),
(8,'2026-04-15 13:55:00','EMITIDO','2026-04-15 14:05:00','SIM'),
(9,'2026-04-15 14:55:00','EMITIDO','2026-04-15 15:10:00','SIM'),
(10,'2026-04-15 15:55:00','NAO_EMITIDO',null,'NAO'),
(11,'2026-04-15 07:55:00','EMITIDO','2026-04-15 08:10:00','SIM'),
(12,'2026-04-15 08:25:00','EMITIDO','2026-04-15 09:00:00','SIM'),
(13,'2026-04-15 09:25:00','NAO_EMITIDO',null,'NAO'),
(14,'2026-04-15 10:25:00','EMITIDO','2026-04-15 10:40:00','SIM'),
(15,'2026-04-15 06:55:00','EMITIDO','2026-04-15 07:05:00','SIM');



