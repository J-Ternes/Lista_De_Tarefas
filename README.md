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
