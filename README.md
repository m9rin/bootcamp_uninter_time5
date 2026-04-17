# 💊 MedAlerta

> **Sistema CLI de Gerenciamento de Medicamentos**  
> Desenvolvido no Bootcamp de Engenharia de Software — UNINTER | Time 5

---

## 📋 Índice

- [Sobre o Projeto](#sobre-o-projeto)
- [Funcionalidades](#funcionalidades)
- [Arquitetura](#arquitetura)
- [Modelo de Dados](#modelo-de-dados)
- [Estrutura de Pastas](#estrutura-de-pastas)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Pré-requisitos](#pré-requisitos)
- [Como Executar](#como-executar)
- [Variáveis de Ambiente](#variáveis-de-ambiente)
- [Banco de Dados](#banco-de-dados)
- [Camadas da Aplicação](#camadas-da-aplicação)
- [Menus da CLI](#menus-da-cli)
- [Pipeline CI/CD](#pipeline-cicd)
- [Infraestrutura Docker](#infraestrutura-docker)
- [Dados de Seed](#dados-de-seed)
- [Boas Práticas Aplicadas](#boas-práticas-aplicadas)
- [Observações Importantes](#observações-importantes)

---

## 📌 Sobre o Projeto

O **MedAlerta** é uma aplicação de linha de comando (CLI) desenvolvida em **Java com Spring Boot**, cujo objetivo é auxiliar usuários no controle e regularidade do uso de medicamentos. O sistema resolve problemas comuns como:

- ❌ Esquecimento de tomar medicamentos
- ❌ Dosagem incorreta
- ❌ Falta de controle sobre horários e frequências de tratamentos
- ❌ Dificuldade em gerenciar múltiplos medicamentos simultaneamente

A aplicação roda via terminal, conecta-se a um banco de dados **MySQL** containerizado e oferece um menu interativo completo para gerenciar usuários, medicamentos, tratamentos e alertas de consumo.

---

## ✅ Funcionalidades

### 👤 Gestão de Usuários
- Cadastrar novo usuário (nome, telefone, e-mail)
- Listar todos os usuários
- Buscar usuário por ID
- Atualizar dados do usuário (campos opcionais — mantém valor atual se deixado em branco)
- Remover usuário

### 🏠 Gestão de Endereços
- Cadastrar endereço vinculado a um usuário
- Listar endereços por usuário
- Buscar endereço por ID
- Atualizar endereço
- Remover endereço

### 💊 Gestão de Medicamentos
- Cadastrar medicamento (nome comercial, nome genérico, forma de uso, quantidade, observação)
- Listar todos os medicamentos
- Buscar medicamento por ID
- Atualizar medicamento
- Remover medicamento
- Busca por nome comercial (parcial, case-insensitive)
- Busca por nome genérico (parcial, case-insensitive)

### 🗓️ Gestão de Tratamentos
- Cadastrar tratamento vinculado a um usuário (horário de uso + frequência)
- Listar todos os tratamentos
- Listar tratamentos por usuário
- Buscar tratamento por ID
- Atualizar tratamento
- Remover tratamento

### 🔔 Gestão de Alertas
- Criar alerta para um tratamento (data/hora + status inicial `NAO_EMITIDO`)
- Listar alertas por tratamento
- Listar alertas não emitidos (pendentes)
- **Registrar consumo**: confirmar se o medicamento foi tomado (`SIM`/`NAO`), atualiza status para `EMITIDO` e registra data/hora do consumo
- Remover alerta

### 🔗 Associação Tratamento ↔ Medicamento
- Associar um medicamento a um tratamento
- Listar medicamentos de um tratamento
- Desassociar medicamento de um tratamento

---

## 🏗️ Arquitetura

O projeto segue o padrão de **Arquitetura em Camadas (Layered Architecture)**:

```
CLI (App.java)
     ↓
Service Layer  (regras de negócio)
     ↓
Repository Layer  (acesso a dados via Spring Data JPA)
     ↓
MySQL Database  (persistência)
```

### Fluxo de execução

1. A aplicação inicia via `MedAlertaApplication.java` (Spring Boot)
2. O `App.java` implementa `CommandLineRunner` e é executado automaticamente
3. O menu interativo lê entradas do usuário via `Scanner`
4. As operações são delegadas aos **Services**
5. Os Services utilizam os **Repositories** (Spring Data JPA) para persistir/consultar dados
6. O banco MySQL é inicializado automaticamente via script SQL no Docker

---

## 🗄️ Modelo de Dados

### Diagrama de Entidades

```
usuario
  ├── id_usuario (PK)
  ├── nome
  ├── telefone
  └── email
       │
       ├──► endereco
       │      ├── id_endereco (PK)
       │      ├── id_usuario (FK)
       │      ├── rua, numero, complemento
       │      ├── bairro, cep, cidade, estado
       │
       └──► tratamento
              ├── id_tratamento (PK)
              ├── id_usuario (FK)
              ├── horario_uso
              ├── frequencia_uso
              │
              ├──► alerta
              │      ├── id_alerta (PK)
              │      ├── id_tratamento (FK)
              │      ├── data_horario_alerta
              │      ├── status_alerta (EMITIDO | NAO_EMITIDO)
              │      ├── data_horario_consumo
              │      └── confirmacao_consumo (SIM | NAO)
              │
              └──► tratamento_medicamento (tabela associativa)
                     ├── id_tratamento (FK, PK composta)
                     ├── id_medicamento (FK, PK composta)
                     ├── quantidade
                     └── observacao

medicamento
  ├── id_medicamento (PK)
  ├── nome_comercial
  ├── nome_generico
  ├── quantidade (UNIDADE | ML)
  ├── forma_uso
  └── observacao
```

### Enumerações

| Enum | Valores |
|------|---------|
| `QuantidadeTipo` | `UNIDADE`, `ML` |
| `StatusAlerta` | `EMITIDO`, `NAO_EMITIDO` |
| `ConfirmacaoConsumo` | `SIM`, `NAO` |

---

## 📁 Estrutura de Pastas

```
bootcamp_uninter_time5/
├── .devcontainer/              # Configuração do Dev Container (VSCode)
├── .github/
│   ├── img/                    # Imagens do README
│   └── workflows/
│       └── ci.yml              # Pipeline GitHub Actions
├── docker/
│   └── mysql/
│       ├── conf.d/
│       │   └── pt-br.cnf       # Configuração de charset MySQL
│       └── init/
│           └── 01-init.sql     # Script de criação e seed do banco
├── docs/
│   └── project/
│       └── diagrams/           # Diagramas PlantUML do projeto
├── src/
│   └── main/
│       ├── java/br/uninter/medalerta/
│       │   ├── app/
│       │   │   └── App.java                        # CLI principal (CommandLineRunner)
│       │   ├── model/
│       │   │   ├── Usuario.java
│       │   │   ├── Endereco.java
│       │   │   ├── Medicamento.java
│       │   │   ├── Tratamento.java
│       │   │   ├── Alerta.java
│       │   │   ├── TratamentoMedicamento.java
│       │   │   ├── TratamentoMedicamentoId.java     # Chave composta @Embeddable
│       │   │   ├── QuantidadeTipo.java              # Enum
│       │   │   ├── StatusAlerta.java                # Enum
│       │   │   └── ConfirmacaoConsumo.java          # Enum
│       │   ├── repository/
│       │   │   ├── UsuarioRepository.java
│       │   │   ├── EnderecoRepository.java
│       │   │   ├── MedicamentoRepository.java
│       │   │   ├── TratamentoRepository.java
│       │   │   ├── AlertaRepository.java
│       │   │   └── TratamentoMedicamentoRepository.java
│       │   ├── service/
│       │   │   ├── UsuarioService.java
│       │   │   ├── EnderecoService.java
│       │   │   ├── MedicamentoService.java
│       │   │   ├── TratamentoService.java
│       │   │   ├── AlertaService.java
│       │   │   └── TratamentoMedicamentoService.java
│       │   └── MedAlertaApplication.java            # Entry point Spring Boot
│       └── resources/
│           ├── application.yml                      # Configuração base
│           └── application-dev.yml                  # Configuração de desenvolvimento
├── .dockerignore
├── .gitignore
├── docker-compose.yml          # Orquestração dos containers
├── Dockerfile                  # Multi-stage build (dev / build / prod)
└── pom.xml                     # Dependências Maven
```

---

## 🛠️ Tecnologias Utilizadas

| Tecnologia | Versão | Uso |
|-----------|--------|-----|
| Java | 21 | Linguagem principal |
| Spring Boot | 3.4.4 | Framework base |
| Spring Data JPA | — | ORM e acesso a dados |
| Hibernate | — | Implementação JPA |
| MySQL | 8.4 | Banco de dados relacional |
| Maven | 3.9.9 | Gerenciamento de dependências |
| Docker | — | Containerização |
| Docker Compose | — | Orquestração local |
| GitHub Actions | — | CI/CD pipeline |
| Eclipse Temurin | 21 | JDK/JRE base das imagens |

---

## ⚙️ Pré-requisitos

- [Docker Desktop](https://www.docker.com/products/docker-desktop/) instalado e em execução
- [VSCode](https://code.visualstudio.com/) (recomendado)
- Extensão **Dev Containers** no VSCode (recomendado)
- Git

> Para rodar localmente sem Docker: JDK 21 + Maven 3.9+ + MySQL 8.4

---

## 🚀 Como Executar

### Opção 1 — Via Docker Compose (recomendado)

```bash
# 1. Clone o repositório
git clone https://github.com/m9rin/bootcamp_uninter_time5.git
cd bootcamp_uninter_time5

# 2. Configure as variáveis de ambiente
cp .env.example .env
# Edite o .env com suas credenciais se necessário

# 3. Suba os containers
docker compose up --build

# 4. Acesse o terminal da aplicação
docker exec -it medalerta-app bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### Opção 2 — Via Maven local

```bash
# Com MySQL rodando localmente na porta 3306
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### Reinicializar o banco do zero

```bash
docker compose down -v
docker compose up --build
```

---

## 🔐 Variáveis de Ambiente

O arquivo `.env` (baseado em `.env.example`) deve conter:

| Variável | Descrição | Padrão |
|----------|-----------|--------|
| `MYSQL_ROOT_PASSWORD` | Senha do root MySQL | — |
| `MYSQL_DATABASE` | Nome do banco | `medalerta` |
| `MYSQL_USER` | Usuário da aplicação | `medalerta_user` |
| `MYSQL_PASSWORD` | Senha do usuário | `medalerta_pass` |
| `SERVER_PORT` | Porta da aplicação | `8080` |

---

## 🗃️ Banco de Dados

### Inicialização automática

O script `docker/mysql/init/01-init.sql` é executado automaticamente na **primeira inicialização** do container MySQL. Ele:

1. Configura o charset `utf8mb4`
2. Cria todas as tabelas com relacionamentos e constraints
3. Popula o banco com **dados de seed** (15 usuários, 10 medicamentos, 15 tratamentos, 15 alertas)

### Acesso externo (MySQL Workbench)

```
Host: localhost
Porta: 3306
Usuário: medalerta_user
Senha: medalerta_pass
Database: medalerta
```

### Configuração JPA (application-dev.yml)

```yaml
spring:
  datasource:
    url: jdbc:mysql://${DB_HOST:db}:${DB_PORT:3306}/${DB_NAME:medalerta}
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

---

## 🧱 Camadas da Aplicação

### Model (Entidades JPA)

Cada entidade mapeia diretamente uma tabela do banco:

| Classe | Tabela | Descrição |
|--------|--------|-----------|
| `Usuario` | `usuario` | Dados do usuário do sistema |
| `Endereco` | `endereco` | Endereço vinculado ao usuário |
| `Medicamento` | `medicamento` | Cadastro de medicamentos |
| `Tratamento` | `tratamento` | Tratamento de um usuário (horário + frequência) |
| `Alerta` | `alerta` | Alerta de consumo de medicamento |
| `TratamentoMedicamento` | `tratamento_medicamento` | Associação N:N entre tratamento e medicamento |
| `TratamentoMedicamentoId` | — | Chave composta `@Embeddable` |

### Repository (Spring Data JPA)

Cada repository estende `JpaRepository` e adiciona queries derivadas:

| Repository | Queries customizadas |
|-----------|---------------------|
| `UsuarioRepository` | — |
| `EnderecoRepository` | `findByUsuario_IdUsuario` |
| `MedicamentoRepository` | `findByNomeComercialContainingIgnoreCase`, `findByNomeGenericoContainingIgnoreCase` |
| `TratamentoRepository` | `findByUsuario_IdUsuario` |
| `AlertaRepository` | `findByTratamento_IdTratamento`, `findByStatusAlerta` |
| `TratamentoMedicamentoRepository` | `findByTratamento_IdTratamento`, `findByMedicamento_IdMedicamento` |

### Service (Regras de Negócio)

Cada service encapsula a lógica de negócio e lança `RuntimeException` com mensagens descritivas quando entidades não são encontradas:

- **`UsuarioService`** — CRUD completo de usuários
- **`EnderecoService`** — CRUD de endereços com vínculo ao usuário
- **`MedicamentoService`** — CRUD + busca por nome
- **`TratamentoService`** — CRUD + listagem por usuário
- **`AlertaService`** — CRUD + listagem por status e por tratamento
- **`TratamentoMedicamentoService`** — Associar/desassociar medicamentos a tratamentos

---

## 🖥️ Menus da CLI

Ao iniciar, a aplicação exibe:

```
==============================
         MedAlerta
  Seu lembrete de medicamentos
==============================

--- MENU PRINCIPAL ---
1 - Usuarios
2 - Enderecos
3 - Medicamentos
4 - Tratamentos
5 - Alertas
6 - Medicamentos de um Tratamento
0 - Sair
```

### Submenu de Alertas (destaque)

```
--- Alertas ---
1 - Criar alerta
2 - Listar por tratamento
3 - Listar nao emitidos
4 - Registrar consumo   ← confirma se o medicamento foi tomado
5 - Remover
0 - Voltar
```

A opção **"Registrar consumo"** é o coração do sistema: ao confirmar o consumo, o alerta muda de `NAO_EMITIDO` → `EMITIDO` e registra o `data_horario_consumo` automaticamente com `LocalDateTime.now()`.

### Formatos de entrada aceitos

| Tipo | Formato |
|------|---------|
| Data e hora | `dd/MM/yyyy HH:mm` |
| Horário | `HH:mm` |
| Inteiros | Validação automática com retry |
| Texto opcional | Enter em branco mantém valor atual |

---

## ⚡ Pipeline CI/CD

O arquivo `.github/workflows/ci.yml` define um pipeline que é acionado em:
- Push nas branches `main`, `develop` e `feature/**`
- Pull Requests para `main` e `develop`

### Etapas do pipeline

```
1. Checkout do código
2. Configurar Java 21 (Temurin) com cache Maven
3. Subir MySQL 8.4 como service (health check automático)
4. Aguardar MySQL ficar pronto (retry com netcat)
5. Executar testes: mvn -B test -Dspring.profiles.active=test
6. Construir imagem de produção: docker build --target prod
```

---

## 🐳 Infraestrutura Docker

### Dockerfile Multi-stage

| Stage | Base | Finalidade |
|-------|------|-----------|
| `dev` | `maven:3.9.9-eclipse-temurin-21` | Ambiente de desenvolvimento com ferramentas extras |
| `build` | `maven:3.9.9-eclipse-temurin-21` | Compila o projeto (`mvn package`) |
| `prod` | `eclipse-temurin:21-jre` | Imagem final enxuta para produção |

### docker-compose.yml

| Container | Imagem | Porta | Função |
|-----------|--------|-------|--------|
| `medalerta-app` | Build local (stage `dev`) | `8080:8080` | Aplicação Spring Boot |
| `medalerta-mysql` | `mysql:8.4` | `3306:3306` | Banco de dados MySQL |

**Recursos configurados:**
- Health check no MySQL antes de subir a aplicação (`depends_on: condition: service_healthy`)
- Volume persistente `mysql_data` para os dados do banco
- Rede interna `medalerta-net` (bridge) para comunicação entre containers
- Scripts de inicialização montados em `/docker-entrypoint-initdb.d`
- Configuração de charset `utf8mb4` via `pt-br.cnf`

---

## 🌱 Dados de Seed

O banco é populado automaticamente com dados de exemplo:

| Tabela | Registros |
|--------|-----------|
| `usuario` | 15 usuários (Ana Souza, Bruno Lima, Carla Mendes...) |
| `endereco` | 15 endereços em Curitiba/PR |
| `medicamento` | 10 medicamentos (Tylenol, Advil, Amoxil, Dipirona...) |
| `tratamento` | 15 tratamentos com horários variados |
| `tratamento_medicamento` | 15 associações |
| `alerta` | 15 alertas com status variados (EMITIDO/NAO_EMITIDO) |

---

## ✨ Boas Práticas Aplicadas

- ✅ **Injeção de dependência via construtor** (sem `@Autowired` em campo)
- ✅ **Separação de responsabilidades** (Model / Repository / Service / App)
- ✅ **Variáveis de ambiente** para credenciais (sem hardcode)
- ✅ **Profiles Spring** (`dev`, `test`) para separar configurações por ambiente
- ✅ **Health check** no Docker Compose para garantir ordem de inicialização
- ✅ **Multi-stage Dockerfile** para imagem de produção enxuta
- ✅ **Chave composta com `@Embeddable`** para relacionamento N:N com atributos
- ✅ **Cascade e orphanRemoval** nas relações `@OneToMany`
- ✅ **Validação de entrada** com retry automático para inteiros e datas
- ✅ **Mensagens de erro descritivas** via `RuntimeException`
- ✅ **Pipeline CI/CD** automatizado com GitHub Actions

---

## ⚠️ Observações Importantes

> Este projeto representa uma **infraestrutura de desenvolvimento educacional**.

Em um ambiente de **produção real**, considere:

- 🔒 Armazenar credenciais em cofres seguros (ex: AWS Secrets Manager, HashiCorp Vault)
- 🗄️ Não executar o banco de dados em container local — use serviços gerenciados (RDS, Cloud SQL)
- 💾 Implementar mecanismos de backup e redundância
- 🔐 Adicionar autenticação e autorização de usuários
- 📊 Implementar monitoramento e observabilidade
- 🧪 Aumentar a cobertura de testes automatizados

---

## 👥 Professores

| Professor | Titulação |
|-----------|-----------|
| Rodrigo da Silva do Nascimento | Prof. Me. |
| Guilherme Patriota | Prof. Me. |
| Neusa Grando | Prof. PhD. |
| Jadson Almeida | Prof. Me. |

---

## 📄 Licença

Projeto desenvolvido para fins educacionais no **Bootcamp de Engenharia de Software — UNINTER**.

---

<div align="center">
  <strong>MedAlerta</strong> — Não esqueça de tomar seus medicamentos 💊
</div>
