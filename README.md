# rr-gestor-api

O **rr-gestor-api** é uma API desenvolvida em Java com o framework Spring Boot, destinada ao gerenciamento de trabalhos. Ela permite o cadastro e a vinculação de clientes e trabalhos, além de gerenciar prazos de entrega e pagamentos associados a cada trabalho.

## Funcionalidades

- **Gerenciamento de Clientes**: Permite criar, atualizar, listar e remover clientes.
- **Gerenciamento de Trabalhos**: Possibilita o cadastro de trabalhos vinculados a clientes, incluindo detalhes como prazos de entrega e condições de pagamento.
- **Prazos de Entrega e Pagamento**: Cada trabalho pode ter múltiplos prazos de entrega e de pagamento associados, facilitando o acompanhamento e a gestão financeira.

## Tecnologias Utilizadas

- **Java 17**: Linguagem de programação utilizada.
- **Spring Boot 3.4.1**: Framework para criação de aplicações Java.
  - *Spring Boot Starter Data JPA*: Para persistência de dados.
  - *Spring Boot Starter Security*: Para configuração de segurança.
  - *Spring Boot Starter Web*: Para construção de APIs RESTful.
  - *Spring Boot Starter Actuator*: Para monitoramento e métricas da aplicação.
- **PostgreSQL**: Banco de dados utilizado.
- **Flyway**: Ferramenta para versionamento e migração de banco de dados.
- **Lombok**: Biblioteca para reduzir o código boilerplate.
- **JWT (JSON Web Token)**: Para autenticação e autorização.

## Pré-requisitos

- **Java 17** instalado.
- **Maven** para gerenciamento de dependências.
- **PostgreSQL** configurado e em execução.

## Configuração do Banco de Dados

Certifique-se de que o PostgreSQL esteja configurado corretamente e crie um banco de dados para a aplicação. As configurações de acesso ao banco de dados devem ser definidas no arquivo `application.properties` ou `application.yml`, incluindo URL, nome de usuário e senha.

## Executando a Aplicação

1. **Clone o repositório**:

   ```bash
   git clone https://github.com/gabrieldaim/rr-gestor-api.git
   cd rr-gestor-api
   ```

2. **Instale as dependências e compile o projeto**:

   ```bash
   mvn clean install
   ```

3. **Execute a aplicação**:

   ```bash
   mvn spring-boot:run
   ```

A aplicação estará disponível em `http://localhost:8080`.

## Endpoints Principais

- **Auth**:
  - `POST /login`: Realiza login na aplicação para obter o token JWT.
  - `POST /atualizarSenha`: Atualiza a senha
  - `GET /listar`: Obtém um resumo de todos os usuários do sistema.
  - `DELETE /{id}`: Remove um usuário.

- **Cliente**:
  - `GET /todosResumo`: Lista um resumo de dados de todos os clientes.
  - `POST /criar`: Cria um novo cliente.
  - `GET /clientes/{id}`: Obtém detalhes de um cliente específico.
  - `PUT /atualizar/{id}`: Atualiza informações de um cliente.
  - `DELETE /{id}`: Remove um cliente.

- **Trabalho**:
  - `GET /todosResumo`: Lista um resumo de dados de todos os trabalhos.
  - `GET /todosResumoParcelas`: Lista um resumo de dados de todos os trabalhos com base nas parcelas de pagamento.
  - `GET /todosResumoEmail/{email}`: Lista um resumo de dados de todos os trabalhos de um email específico.
  - `POST /criar`: Cria um novo trabalho.
  - `GET /trabalho/{id}`: Obtém detalhes de um trabalho específico.
  - `PUT /atualizar/{id}`: Atualiza informações de um trabalho.
  - `DELETE /{id}`: Remove um trabalho.

## Segurança

A API utiliza autenticação baseada em JWT. Para acessar os endpoints protegidos, é necessário incluir um token JWT válido no cabeçalho das requisições.

## Documentação da API

A documentação detalhada dos endpoints, incluindo parâmetros e exemplos de requisição/resposta, pode ser acessada através do Swagger UI, disponível em `http://localhost:8080/swagger-ui.html`.

## Contribuição

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues e enviar pull requests.

## Licença

Este projeto está licenciado sob a [MIT License](LICENSE).




