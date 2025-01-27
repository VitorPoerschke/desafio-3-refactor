# Microserviços de Eventos e Tickets

Este repositório contém dois microserviços principais:

1. **Microserviço de Eventos** - Gerencia eventos, permitindo criar, buscar, listar, atualizar e excluir eventos.
2. **Microserviço de Tickets** - Gerencia tickets, incluindo criação, busca, atualização e exclusão.

Além disso, os microserviços utilizam integração com RabbitMQ para envio de e-mails e MongoDB para armazenamento de dados.

---

## Funcionalidades

### Microserviço de Eventos

- **Criar Evento**: Cria um novo evento com os dados fornecidos.
- **Buscar Evento por ID**: Retorna os detalhes de um evento usando seu identificador.
- **Listar Todos os Eventos**: Retorna todos os eventos cadastrados.
- **Listar Eventos Ordenados**: Retorna todos os eventos em ordem específica.
- **Atualizar Evento**: Atualiza os detalhes de um evento existente.
- **Excluir Evento**: Remove um evento pelo seu ID.
- **Testar Servidor**: Verifica se o servidor de eventos está ativo.

### Microserviço de Tickets

- **Criar Ticket**: Cria um ticket com os detalhes fornecidos.
- **Buscar Ticket por ID**: Retorna os detalhes de um ticket pelo seu identificador.
- **Listar Todos os Tickets**: Retorna todos os tickets cadastrados.
- **Atualizar Ticket**: Atualiza os detalhes de um ticket existente.
- **Excluir Ticket**: Remove um ticket pelo seu ID.
- **Testar Servidor**: Verifica se o servidor de tickets está ativo.

---

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot** (com módulos como Spring Data, Spring Web e Spring Mail)
- **MongoDB** (com MongoDB Compass para gerenciamento)
- **RabbitMQ** (para envio de e-mails assíncronos)
- **Postman** (para testes de API)

---

## Configurações Necessárias

1. **Configurar RabbitMQ para envio de e-mails:**

   Crie um arquivo chamado `application-secrets.properties` com as seguintes configurações:
   
   ```properties
   # Configurações do Gmail
   spring.mail.host=smtp.gmail.com
   spring.mail.port=587
   spring.mail.username=seu_email
   spring.mail.password=sua_senha_de_aplicativo
   spring.mail.properties.mail.smtp.auth=true
   spring.mail.properties.mail.smtp.starttls.enable=true
   ```

   Substitua `seu_email` e `sua_senha_de_aplicativo` pelas credenciais do seu e-mail e senha do aplicativo gerada no Gmail.

2. **MongoDB**:
   - Certifique-se de que o MongoDB está configurado e em execução em sua máquina.
   - O banco de dados será gerado automaticamente com base nas entidades `Event` e `Ticket`.

3. **RabbitMQ**:
   - Certifique-se de que o RabbitMQ está instalado e configurado em sua máquina.

---

## Endpoints Disponíveis

### Microserviço de Eventos

| Método | Endpoint                 | Descrição                                     |
|--------|--------------------------|---------------------------------------------|
| POST   | `/create-event`          | Cria um novo evento.                        |
| GET    | `/get-event/{id}`        | Busca um evento pelo ID.                    |
| GET    | `/get-all-events`        | Lista todos os eventos.                     |
| GET    | `/get-all-events/sorted` | Lista todos os eventos em ordem específica. |
| PUT    | `/update-event/{id}`     | Atualiza os detalhes de um evento.          |
| DELETE | `/delete-event/{id}`     | Remove um evento pelo ID.                   |
| GET    | `/test`                  | Testa o funcionamento do servidor.          |

### Microserviço de Tickets

| Método | Endpoint            | Descrição                          |
|--------|---------------------|----------------------------------|
| POST   | `/create-ticket`    | Cria um novo ticket.            |
| GET    | `/get/{id}`         | Busca um ticket pelo ID.        |
| GET    | `/get-all-tickets`  | Lista todos os tickets.         |
| PUT    | `/update-ticket/{id}`| Atualiza os detalhes de um ticket.|
| DELETE | `/delete/{id}`      | Remove um ticket pelo ID.       |
| GET    | `/test`             | Testa o funcionamento do servidor. |

---

## Como Executar o Projeto

1. Clone o repositório:
   ```bash
   git clone https://github.com/VitorPoerschke/desafio-3-refactor.git
   ```
2. Navegue até o diretório do projeto.

3. Configure o arquivo `application-secrets.properties` conforme descrito anteriormente.

4. Inicie os microserviços:
   ```bash
   ./mvnw spring-boot:run
   ```

5. Use o Postman ou outra ferramenta de sua preferência para testar os endpoints.

---

## Autor

- **Seu Nome** - [GitHub](https://github.com/VitorPoerschke)

