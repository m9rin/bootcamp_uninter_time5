ALTER DATABASE medalerta
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

use medalerta;

create table Usuario (
	idUsuario int auto_increment not null,
	nome varchar(100) not null,
    telefone varchar(20) not null,
    email varchar(100) not null,
    primary key (idUsuario)
);

create table Endereco (
	idEndereco int auto_increment not null,
	idUsuario int auto_increment not null,
    enderecoRua varchar(100),
    enderecoNumero int,
    enderecoComplemento varchar(50),
    enderecoBairro varchar(50),
    enderecoCEP varchar(10),
    enderecoCidade varchar(50),
    enderecoEstado varchar(02),
    primary key (idEndereco)
	foreign key (idUsuario) references Usuario (idUsuario)
);
create table Medicamento (
	idMedicamento int auto_increment not null,
    nomeComercial varchar(100) not null,
    nomeGenerico varchar(100),
    quantidade enum('unidade', 'ml'),
    formaUso varchar(100),
    observacao varchar(200),
    primary key (idMedicamento)
);

create table Tratamento (
	idTratamento int not null,
	idUsuario int not null,
    horarioUso time not null,
    frequenciaUso varchar(50),
    primary key (idTratamento),
    foreign key (idUsuario) references Usuario (idUsuario),
);

create table Alerta (
	idAlerta int not null,
	idTratamento int not null,
    dataHorarioAlerta datetime not null,
    statusAlerta enum('emitido', 'não emitido') not null,
    dataHorarioConsumo datetime,
    confirmacaoConsumo enum('sim', 'não') not null,
    primary key (idAlerta),
    foreign key (idTratamento) references Tratamento (idTratamento),
);

create table Tratamento_Medicamento (
	idTratamento int not null,
	idMedicamento int not null,
	quantidade int not null,
	observacao varchar(50),
    primary key (idTratamento, idMedicamento),
    foreign key (idTratamento) references Tratamento (idTratamento),
	foreign key (idMedicamento) references Medicamento (idMedicamento)
);


-- Inserindo Usuários
INSERT INTO Usuario (nome, telefone, email) VALUES
('Ana Souza', '41999990001', 'ana.souza@email.com'),
('Bruno Lima', '41999990002', 'bruno.lima@email.com'),
('Carla Mendes', '41999990003', 'carla.mendes@email.com'),
('Daniel Rocha', '41999990004', 'daniel.rocha@email.com'),
('Elisa Torres', '41999990005', 'elisa.torres@email.com');

-- Inserindo Endereços
INSERT INTO Endereco (idUsuario, enderecoRua, enderecoNumero, enderecoBairro, enderecoCEP, enderecoCidade, enderecoEstado) VALUES
(1, 'Rua A', 10, 'Centro', '80000-001', 'Curitiba', 'PR'),
(2, 'Rua B', 20, 'Batel', '80000-002', 'Curitiba', 'PR');

-- Inserindo Medicamentos
INSERT INTO Medicamento (nomeComercial, nomeGenerico, quantidade_tipo, formaUso) VALUES
('Tylenol', 'Paracetamol', 'unidade', 'Via oral'),
('Advil', 'Ibuprofeno', 'unidade', 'Via oral'),
('Amoxil', 'Amoxicilina', 'ml', 'Via oral');

-- Criando Tratamentos (Vinculando Usuário ao Horário)
INSERT INTO Tratamento (idUsuario, horarioUso, frequenciaUso) VALUES
(1, '08:00:00', '8 em 8 horas'),
(1, '20:00:00', '1 vez ao dia'),
(2, '09:00:00', '12 em 12 horas');

-- Vinculando Medicamentos aos Tratamentos
INSERT INTO Tratamento_Medicamento (idTratamento, idMedicamento, quantidade) VALUES
(1, 1, 1), -- Tratamento 1 usa Tylenol
(2, 2, 1), -- Tratamento 2 usa Advil
(3, 3, 10); -- Tratamento 3 usa Amoxil

-- Gerando Alertas
INSERT INTO Alerta (idTratamento, dataHorarioAlerta, statusAlerta, dataHorarioConsumo, confirmacaoConsumo) VALUES
(1, '2026-04-15 07:55:00', 'emitido', '2026-04-15 08:05:00', 'sim'),
(3, '2026-04-15 08:55:00', 'não emitido', NULL, 'não');

/* Listar todos os usuários, inclusive os que não utilizam nenhum medicamento. */
select Usuario.nome as 'Usuário'
from Usuario;

/* Listar os usuários e os medicamentos que os mesmos utilizam. */
select Usuario.nome as 'Usuário', Medicamento.nomeComercial as 'Medicamento'
from Usuario, Medicamento, Tratamento
where Usuario.idUsuario = Tratamento.idUsuario and
	Medicamento.idMedicamento = Tratamento.idMedicamento;

/* Listar os medicamentos utilizados por um usuário específico. */
select Usuario.nome as 'Usuário', Medicamento.nomeComercial as 'Medicamento', Tratamento.dosagem as 'Dosagem'
from Usuario, Medicamento, UsuarioMedicamento
where Usuario.idUsuario = UsuarioMedicamento.idUsuario and
	Medicamento.idMedicamento = UsuarioMedicamento.idMedicamento and
	Usuario.nome = 'Ana Souza';

/* Listar os usuários que não confirmaram o consumo do medicamento. */
select Usuario.nome as 'Usuário', Medicamento.nomeComercial as 'Medicamento', UsuarioMedicamento.confirmacaoConsumo as 'Confirmação'
from Usuario, Medicamento, UsuarioMedicamento
where Usuario.idUsuario = UsuarioMedicamento.idUsuario and
	Medicamento.idMedicamento = UsuarioMedicamento.idMedicamento and
	UsuarioMedicamento.confirmacaoConsumo = 'não';

/* Listar os alertas já emitidos. */
select Usuario.nome as 'Usuário', Medicamento.nomeComercial as 'Medicamento', Alerta.dataHorarioAlerta as 'Alerta'
from Usuario, Medicamento, Alerta
where Usuario.idUsuario = UsuarioMedicamento.idUsuario and
	Medicamento.idMedicamento = Alerta.idAlerta and
	UsuarioMedicamento.statusAlerta = 'emitido';

/* Listar a quantidade de medicamentos por usuário. */
select U.nome as 'Usuário', count(UM.idMedicamento) as 'Total Medicamento'
from Usuario U
left join UsuarioMedicamento UM on U.idUsuario = UM.idUsuario
group by U.idUsuario;

/* Listar a quantidade de usuários por medicamento. */
select M.nomeComercial as 'Medicamento', count(UM.idUsuario) as 'Total Usuário'
from Medicamento M
left join Tratamento UM on M.idMedicamento = UM.idMedicamento
group by M.idMedicamento;

/* Listar os usuários que consumiram medicamento em determinada data. */
select U.nome as 'Usuário', M.nomeComercial as 'Medicamento', UM.dataHorarioConsumo as 'Consumo'
from Usuario U
join UsuarioMedicamento UM on U.idUsuario = UM.idUsuario
join Medicamento M on M.idMedicamento = UM.idMedicamento
where date(UM.dataHorarioConsumo) = '2026-04-15';

/* Listar a quantidade de alertas não emitidos por usuário. */
select U.nome as 'Usuário', count(*) as 'Alertas não emitidos'
from Usuario U
join UsuarioMedicamento UM on U.idUsuario = UM.idUsuario
where UM.statusAlerta = 'não emitido'
group by U.idUsuario;

/* Listar um resumo geral do uso de medicamentos. */
select U.nome as 'Usuário', M.nomeComercial as 'Medicamento', UM.horarioUso as 'Horário de uso',
	UM.frequenciaUso as 'Frequência de uso', UM.confirmacaoConsumo as 'Confirmação de consumo'
from Usuario U
join UsuarioMedicamento UM on U.idUsuario = UM.idUsuario
join Medicamento M ON M.idMedicamento = UM.idMedicamento
order by U.nome, UM.horarioUso;