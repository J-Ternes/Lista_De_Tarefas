# ToDoList

## Stacks utilizadas:
- Java com Spring Boot;
- Spring Security;
- Flyway;
- JPA;
- PostgreSQL;
- Arquitetura MVC

---
## Sobre:
Projeo Backend de uma simples lista de tarefa criado para praticar a teoria estudada em Java. É possível criar tarefas com títulos, data de início, data de término e uma descrição.
Utilizei o Spring Security para autenticação e autorização via JWT token. Criei duas roles: ADMIN ou USER. O usuário com a role ADMIN consegue criar novas tarefas e ver tarefas de outros usuários, além de promover um USER para ADMIN.
O usuário com a role USER apenas consegue ver a sua tarefa.
Existem duas tabelas: Users e Tasks. Construí uma relação One to Many (User -> Task).

O projeto ainda está sendo desenvolvido com melhorias e novas features.
