-- Arquivo para dados iniciais.
-- Adicione inserts de exemplo para nossos testes e correção.
-- Exemplo simples de inserção de dados.
-- Mostraremos 2 tabelas com 3 colunas cada e relacionadas entre si.
USE medalerta;
CREATE TABLE IF NOT EXISTS users (
		id INT PRIMARY KEY AUTO_INCREMENT,
		name VARCHAR(255),
		email VARCHAR(255)
);
CREATE TABLE IF NOT EXISTS posts (
		id INT PRIMARY KEY AUTO_INCREMENT,
		title VARCHAR(255),
		content TEXT,
		user_id INT,
		FOREIGN KEY (user_id) REFERENCES users(id)
);
INSERT IGNORE INTO users (name, email) VALUES ('Alice', 'alice@example.com');
INSERT IGNORE INTO posts (title, content, user_id) VALUES ('Primeira Postagem', 'Conteúdo da primeira postagem.', 1);

CREATE DATABASE MedAlerta;
USE MedAlerta;



CREATE TABLE Usuario (
	idUsuario int primary key not null,
	nome varchar(15) not null,
	telefone int (14) not null,
	email varchar (25) not null,
	endereço varchar (20),
	endereçoRua varchar (20),
	endereçoNumero varchar (20),
	endereçoComp varchar (20),
	endereçoBairro varchar (20),
	endereçoCEP varchar (20),
	endereçoCidade varchar (20),
	endereçoEstado varchar (20)
);

CREATE TABLE usuarioMedicamento(
	IdUsuario int not null,
	IdMedicamento int not null,
	horarioUso time not null,
	frequenciaUso varchar(50),
	dosagem varchar(50),
	dataHOrarioAlerta datetime not null,
	statusAlerta enum('emitido', 'Nao emitido');
	dataHorarioConsumo datetime,
	confirmacaoConsumo enum('sim', 'nao') not null,
	primary key (IdUsuario, IdMedcimaento),
	foreign key (IdUsuario) references (IdUsuario),
	foreign key (IdMedcimaento) references Medicamento (IdMedicamento)
);

CREATE TABLE Medicamento (
	IdMedcimaento int primary key,
	nomeComercial varchar(100),
	nomeGenerico varchar(100),
	quantidade enum('unidade', 'ml'),
	formaUso varchar(100),
	observacao varchar(200)	
);

