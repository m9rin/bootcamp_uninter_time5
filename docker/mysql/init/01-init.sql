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
	IdUsuario INT PRIMARY KEY NOT NULL,
	Nome VARCHAR (100) NOT NULL,
	Telefone VARCHAR (10) NOT NULL,
	Email VARCHAR (100) NOT NULL,
	EnderecoRua VARCHAR (100),
	EnderecoNumero INT (10),
	EnderecoComplemento VARCHAR (50),
	EnderecoBairro VARCHAR (50),
	EnderecoCEP VARCHAR (10),
	EnderecoCidade VARCHAR (50),
	EnderecoEstado CHAR (02)
);

CREATE TABLE UsuarioMedicamento(
	IdUsuario INT NOT NULL,
	IdMedicamento INT NOT NULL,
	HorarioUso time NOT NULL,
	FrequenciaUso VARCHAR(50),
	Dosagem VARCHAR(50),
	DataHOrarioAlerta datetime NOT NULL,
	StatusAlerta ENUM('emitido', 'Nao emitido');
	DataHorarioConsumo datetime,
	ConfirmacaoConsumo enum('sim', 'nao') NOT NULL,
	PRIMARY KEY (IdUsuario, IdMedcimaento),
	FOREIGN KEY (IdUsuario) REFERENCES Usuario(IdUsuario),
	FOREIGN KEY (IdMedcimaento) REFERENCES Medicamento(IdMedicamento)
);

CREATE TABLE Medicamento (
	IdMedcimaento INT primary key,
	NomeComercial VARCHAR(100),
	NomeGenerico VARCHAR(100),
	Quantidade ENUM('unidade', 'ml'),
	FormaUso VARCHAR(100),
	Observacao VARCHAR(200)	
);

