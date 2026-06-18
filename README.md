# MedAlerta

> **Sistema CLI para gerenciamento de medicamentos, tratamentos e alertas de consumo.**
>
> Desenvolvido no Bootcamp de Engenharia de Software — UNINTER | Time 5

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen)
![MySQL](https://img.shields.io/badge/MySQL-8.x-blue)
![Docker](https://img.shields.io/badge/Docker-ready-2496ED)
![License](https://img.shields.io/badge/Licença-Educacional-lightgrey)

Sistema CLI desenvolvido em Java com Spring Boot que ajuda usuários a organizar tratamentos, medicamentos associados e alertas de consumo de forma simples e objetiva.

---

## Sumário

- [Pré-requisitos](#pré-requisitos)
- [Arquitetura](#arquitetura)
- [Modelo de Domínio](#modelo-de-domínio)
- [Entidades](#entidades)
- [Decisões de Modelagem](#decisões-de-modelagem)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Funcionalidades](#funcionalidades)
- [Banco de Dados](#banco-de-dados)
- [Configuração](#configuração)
- [Como Executar](#como-executar)
- [Testes](#testes)
- [Exemplo de Uso da CLI](#exemplo-de-uso-da-cli)
- [Diagramas](#diagramas)
- [Observações Importantes](#observações-importantes)
- [Equipe](#equipe)
- [Licença](#licença)

---

## Pré-requisitos

Antes de executar o projeto, certifique-se de ter instalado:

| Ferramenta | Versão mínima |
|---|---|
| Java (JDK) | 21+ |
| Maven | 3.9+ |
| Docker | 24+ |
| Docker Compose | 2.x |
| MySQL *(execução local)* | 8.x |

---

## Arquitetura

O projeto segue uma arquitetura em camadas, mantida propositalmente simples para o contexto de uma aplicação CLI:

```text
CLI (App.java)
  -> Service
  -> Repository
  -> JPA / MySQL
```

---

## Modelo de Domínio

```text
Usuario
  └── Tratamento
        ├── Alerta
        └── TratamentoMedicamento
               └── Medicamento
```

> `Endereco` foi removido por não participar das regras atuais da aplicação.

---

## Entidades

| Entidade | Responsabilidade |
|---|---|
| `Usuario` | Dados básicos do usuário |
| `Medicamento` | Catálogo de medicamentos disponíveis |
| `Tratamento` | Plano de tratamento vinculado a um usuário |
| `TratamentoMedicamento` | Medicamento dentro de um tratamento, com dose, horário e frequência |
| `Alerta` | Evento de lembrete e registro de consumo |

---

## Decisões de Modelagem

- `TratamentoMedicamento` usa ID próprio (`id_tratamento_medicamento`) para simplificar o mapeamento JPA.
- A constraint `UNIQUE (id_tratamento, id_medicamento)` impede associar o mesmo medicamento duas vezes ao mesmo tratamento.
- `horario_uso`, `frequencia_uso`, `quantidade` e `observacao` ficam em `tratamento_medicamento`, pois são propriedades do uso de um medicamento dentro de um tratamento.
- `Medicamento.unidadeMedida` representa o tipo de medida (`UNIDADE` ou `ML`), não a dose real.
- **Lombok** reduz boilerplate de getters, setters e construtores.
- **Bean Validation** documenta regras simples diretamente no modelo.

---

## Estrutura do Projeto

```text
src/main/java/br/uninter/medalerta
├── app
│   └── App.java
├── model
│   ├── Alerta.java
│   ├── ConfirmacaoConsumo.java
│   ├── Medicamento.java
│   ├── QuantidadeTipo.java
│   ├── StatusAlerta.java
│   ├── Tratamento.java
│   ├── TratamentoMedicamento.java
│   └── Usuario.java
├── repository
│   ├── AlertaRepository.java
│   ├── MedicamentoRepository.java
│   ├── TratamentoMedicamentoRepository.java
│   ├── TratamentoRepository.java
│   └── UsuarioRepository.java
├── service
│   ├── AlertaService.java
│   ├── MedicamentoService.java
│   ├── TratamentoService.java
│   └── UsuarioService.java
└── MedAlertaApplication.java
```

---

## Funcionalidades

- [x] CRUD de usuários
- [x] CRUD de medicamentos
- [x] CRUD de tratamentos
- [x] Associação de medicamentos a tratamentos com dose, horário e frequência
- [x] Criação e listagem de alertas
- [x] Registro de consumo de medicamento

---

## Banco de Dados

O MySQL é inicializado automaticamente pelo script:

```text
docker/mysql/init/01-init.sql
```

O script cria as tabelas, constraints e dados de exemplo.

---

## Configuração

As configurações da aplicação ficam em `src/main/resources/`. O perfil `dev` é utilizado para desenvolvimento local.

Principais propriedades em `application-dev.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/medalerta
spring.datasource.username=SEU_USUARIO
spring.datasource.password=SUA_SENHA
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true
```

> ⚠️ **Nunca versione credenciais reais.** Use variáveis de ambiente ou um gerenciador de segredos em ambientes além do desenvolvimento local.

---

## Como Executar

### Com Docker (recomendado)

```bash
docker compose up --build
```

Em seguida, acesse o container da aplicação e execute:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### Localmente (sem Docker)

Certifique-se de ter JDK 21, Maven e uma instância MySQL em execução, então:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

---

## Testes

Para executar os testes automatizados:

```bash
mvn test
```

> A cobertura de testes atual é básica. Expansão da suíte de testes está listada como melhoria futura.

---

## Exemplo de Uso da CLI

Ao iniciar a aplicação, o menu principal é exibido no terminal:

```
============================================
          Bem-vindo ao MedAlerta
============================================
1 - Gerenciar Usuários
2 - Gerenciar Medicamentos
3 - Gerenciar Tratamentos
4 - Gerenciar Alertas
0 - Sair
--------------------------------------------
Escolha uma opção: _
```

Exemplo — fluxo de cadastro de tratamento:

```
[Tratamentos] > 1 - Novo Tratamento
Informe o ID do usuário: 1
Nome do tratamento: Controle de Pressão
Data de início (dd/MM/yyyy): 01/06/2025
Data de término (dd/MM/yyyy): 30/06/2025
Tratamento cadastrado com sucesso! ID: 3
```

---

## Diagramas

Os diagramas atualizados ficam em:

```text
docs/project/diagrams/MER.png   ← Modelo Entidade-Relacionamento
docs/project/diagrams/MR.png    ← Modelo Relacional
```

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

## Equipe

### 👩‍💻 Desenvolvedores

| Nome              | Github                                                   |
|-------------------|----------------------------------------------------------|
| Gabriel Marin     | [@m9rin](https://github.com/m9rin)                       |
| Cris Cunha        | [@engcriscunha](https://github.com/engcriscunha)         |
| Gleyce Pires      | [@GleycePires](https://github.com/GleycePires)           |
| Lucas F. Santana  | [@lsprm](https://github.com/lsprm)                       |
| David M. Sousa    | [@davidmaia](https://github.com/davidsmaia)              |
| Leonardo Miorando | [@leonardomiorando](https://github.com/leonardomiorando) |
| Bruna M. Linhares | [@brunameili](https://github.com/BrunaMeili)             |

### 👥 Professores Orientadores

| Professor | Titulação |
|---|---|
| Rodrigo da Silva do Nascimento | Prof. Me. |
| Guilherme Patriota | Prof. Me. |
| Neusa Grando | Prof. PhD. |
| Jadson Almeida | Prof. Me. |

---

## 📄 Licença

Projeto desenvolvido para fins educacionais no **Bootcamp de Engenharia de Software — UNINTER**.
