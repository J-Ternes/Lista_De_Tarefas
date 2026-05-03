# 📋 Lista de Tarefas (To-Do API) — Spring Boot + Spring Security (JWT)

API REST para gerenciamento de tarefas com autenticação **stateless** via **JWT**, controle de acesso por **roles (RBAC)** e vínculo correto das tarefas ao usuário autenticado (`user_id` nas tasks). O projeto também possui **soft delete** e **limpeza automática (cleanup)** de registros inativos por agendamento.

---

## 🚀 Principais Features

- ✅ **Autenticação via JWT (stateless)**
- ✅ **Autorização / RBAC (Role-Based Access Control)**
- ✅ **Vinculação de Task ao usuário autenticado**
- ✅ **Soft Delete**
- ✅ **Hard Delete automático (Cleanup Agendado)**
- ✅ **Auditoria de datas (`criadoEm`, `atualizadoEm`)**
- ✅ **Validações de regra de negócio**
- ✅ **Tratamento de erros HTTP (401, 403)**

---

## 🛠️ Stack / Tecnologias

| Tecnologia | Uso |
|---|---|
| Java | Linguagem principal |
| Spring Boot | Framework base |
| Spring Web | Camada REST |
| Spring Data JPA / Hibernate | Persistência |
| Spring Security | Autenticação e Autorização |
| JWT (Auth0 Java JWT) | Geração e validação de tokens |
| H2 | Banco de dados em desenvolvimento |
| PostgreSQL | Banco de dados em produção |
| Lombok | Redução de boilerplate |

---

## 🏗️ Arquitetura

### Autenticação (JWT)

1. Cliente faz `POST /auth/login` com credenciais.
2. API valida credenciais e gera um JWT contendo:
   - `sub` (subject): `login`
   - `role` (claim): `ADMIN` ou `USER`
3. Cliente envia o JWT no header:
Authorization: Bearer

4. O `SecurityFilter` (`OncePerRequestFilter`):
- Extrai o token do header
- Valida assinatura e expiração
- Lê os *Claims* (`login`, `role`)
- Cria um `UsernamePasswordAuthenticationToken` com **authorities**
- Popula o `SecurityContextHolder`
5. A partir daí:
- `@PreAuthorize(...)` funciona ✅
- `@AuthenticationPrincipal` funciona ✅

> **Por que "popular o SecurityContext a cada requisição"?**
> Porque em arquitetura stateless não existe sessão no servidor. Então, em **toda request**, o servidor reconstitui "quem é o usuário" a partir do JWT.

---

## 🗄️ Modelo de Dados

### `UserModel` (usuários)

| Campo | Tipo | Descrição |
|---|---|---|
| `id` | UUID | Chave primária (gerada automaticamente) |
| `login` | String | Único, obrigatório |
| `password` | String | Hash BCrypt |
| `role` | Enum | `ADMIN` ou `USER` |
| `active` | Boolean | Soft delete |
| `criadoEm` | LocalDateTime | Preenchido via `@PrePersist` |
| `atualizadoEm` | LocalDateTime | Preenchido via `@PreUpdate` |

### `TaskModel` (tarefas)

| Campo | Tipo | Descrição |
|---|---|---|
| `id` | UUID | Chave primária (gerada automaticamente) |
| `titulo` | String | Título da tarefa |
| `descricao` | String | Descrição da tarefa |
| `dataInicio` | LocalDateTime | Data de início |
| `dataFim` | LocalDateTime | Data de término (deve ser após `dataInicio`) |
| `finalizarTarefa` | Boolean | Status de conclusão (soft delete) |
| `atualizadoEm` | LocalDateTime | Preenchido via `@PreUpdate` |
| `user` | UserModel | FK `user_id` — dono da tarefa |

> **Importante:** O `user_id` da task **não vem do client** (evita fraude).
> O dono da task é definido a partir do usuário autenticado no `SecurityContext` via `@AuthenticationPrincipal`.

### Relacionamento

UserModel (1) ----< TaskModel (N)


- `@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)` no `UserModel`
- `@ManyToOne @JoinColumn(name = "user_id")` no `TaskModel`

---

## 🔐 Segurança (detalhes técnicos)

### Spring Security Filter Chain (stateless)

- CSRF desabilitado (API stateless)
- Session `STATELESS`
- Filtro JWT rodando antes do `UsernamePasswordAuthenticationFilter`

### Controle de Acesso (RBAC)

ADMIN → ROLE_ADMIN + ROLE_USER
USER → ROLE_USER


- Regras por rota via `requestMatchers`
- Regras por método via `@PreAuthorize`

### Tratamento de erros de segurança

| Código | Causa | Onde é tratado |
|---|---|---|
| **401** Unauthorized | Token ausente ou inválido | `authenticationEntryPoint` no `SecurityConfig` |
| **403** Forbidden | Token válido, sem permissão | `accessDeniedHandler` no `SecurityConfig` |

---

## ⚙️ Jobs de Limpeza Automática (Cleanup)

Serviços agendados com `@Scheduled` que rodam diariamente à meia-noite (`cron = "0 0 0 * * *"`).

### Tasks Cleanup
- Busca tasks com `finalizarTarefa = true` e `atualizadoEm` anterior ao tempo de corte
- Remove permanentemente do banco (`deleteAll`)

### Users Cleanup
- Busca usuários com `active = false` e `atualizadoEm` anterior ao tempo de corte (ex.: 30 dias)
- Remove permanentemente do banco, incluindo todas as tasks vinculadas (via `CascadeType.ALL`)

> **Dica:** Durante testes, use `minusMinutes(1)` como tempo de corte.
> Em produção, use `minusDays(7)` ou `minusDays(30)`.

---

## ✅ Validações de Regra de Negócio

| Regra | HTTP Response |
|---|---|
| `dataFim` anterior a `dataInicio` | `400 Bad Request` |
| Login duplicado no cadastro | `409 Conflict` |
| Usuário não encontrado | `404 Not Found` |
| Admin tentando rebaixar a si próprio | `400 Bad Request` |
| Rebaixar o único admin do sistema | `400 Bad Request` |

---

## 📡 Endpoints

### Autenticação (público)

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/auth/login` | Realiza login e retorna o token JWT |
| `POST` | `/users/cadastro` | Cadastro de novo usuário (role: USER) |

### Usuários (protegido)

| Método | Rota | Role | Descrição |
|---|---|---|---|
| `GET` | `/users` | ADMIN | Lista todos os usuários |
| `GET` | `/users/role/{role}` | ADMIN | Busca usuários por role |
| `PUT` | `/users/{id}/promote` | ADMIN | Promove usuário para ADMIN |
| `PUT` | `/users/{id}/demote` | ADMIN | Rebaixa ADMIN para USER |
| `DELETE` | `/users/{login}` | ADMIN | Soft delete de usuário |

### Tasks (protegido)

| Método | Rota | Role | Descrição |
|---|---|---|---|
| `POST` | `/tasks` | USER | Cria task vinculada ao usuário logado |
| `GET` | `/tasks` | USER | Lista tasks do usuário logado |
| `PUT` | `/tasks/{id}` | USER | Atualiza task |
| `DELETE` | `/tasks/{id}` | USER | Soft delete da task |

---

## ▶️ Como rodar o projeto localmente

### Pré-requisitos

- Java 17+
- Maven

### Passos

1. Clone o repositório:
   ```bash
   git clone https://github.com/J-Ternes/Lista_De_Tarefas.git
   cd Lista_De_Tarefas
   ```
2. Configure o banco no `application.properties`:
   ```properties
   # H2 (desenvolvimento)
   spring.datasource.url=jdbc:h2:mem:todolist
   spring.jpa.hibernate.ddl-auto=update

   # PostgreSQL (produção)
   # spring.datasource.url=jdbc:postgresql://localhost:5432/todolist
   # spring.datasource.username=seu_usuario
   # spring.datasource.password=sua_senha
   ```
3. Rode a aplicação:
   ```bash
   ./mvnw spring-boot:run
   ```
4. Teste via Postman ou Insomnia:
   - Faça login → pegue o token JWT
   - Use `Authorization: Bearer <token>` nas rotas protegidas



---
