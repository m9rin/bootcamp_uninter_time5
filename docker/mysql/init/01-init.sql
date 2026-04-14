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