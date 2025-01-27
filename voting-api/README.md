
# Voting API - Agenda Simulator Challenge

## Descrição
A aplicação "Voting API - Agenda Simulator" simula um sistema de votação. Permite criar **agendas**, registrar **sessões de votação** e fazer a **contagem de votos**. O projeto foi desenvolvido com **Spring Boot** e usa **PostgreSQL** como banco de dados. Ele segue boas práticas de arquitetura e design, incluindo uma configuração de JPA para comunicação com o banco de dados e integração com **Swagger** para documentação e teste das APIs.

---

## Requisitos para Execução

- **Java 17+**: Certifique-se de ter o JDK 17 ou superior instalado na sua máquina.
- **Maven**: Para gerenciar as dependências do projeto.
- **PostgreSQL**: O banco de dados utilizado é o PostgreSQL.
- **Ferramenta para Testar APIs**: Use uma ferramenta como **Postman**, **CURL** ou **Swagger UI** para testar as APIs.

---

## Configuração do Projeto

1. **Configuração do Banco de Dados**

   O arquivo `src/main/resources/application.properties` contém a configuração de conexão com o banco de dados. As configurações para o PostgreSQL são as seguintes:

   ```properties
   # Informações da aplicação
   spring.application.name=voting-api

   # Configuração do banco de dados
   spring.datasource.url=jdbc:postgresql://localhost:5432/agenda-simulator-db
   spring.datasource.username=postgres
   spring.datasource.password=admin

   # Configuração do JPA
   spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true

   # Configuração do Swagger
   springdoc.api-docs.enabled=true
   springdoc.swagger-ui.enabled=true

   # Porta do servidor
   server.port=8081
   ```

    - **`spring.datasource.url`**: URL de conexão com o banco de dados PostgreSQL.
    - **`spring.datasource.username`**: Nome de usuário para conexão com o banco de dados.
    - **`spring.datasource.password`**: Senha para o banco de dados.
    - **`spring.jpa.hibernate.ddl-auto`**: Controla o comportamento do Hibernate em relação ao banco de dados (no caso, `update` atualiza o esquema do banco conforme necessário).
    - **`springdoc.api-docs.enabled`**: Habilita a geração da documentação da API.
    - **`springdoc.swagger-ui.enabled`**: Habilita a interface Swagger UI para testar as APIs.

---

## Dependências Maven

O projeto utiliza as seguintes dependências principais, que estão no arquivo `pom.xml`:

- **Spring Boot Starter Web**: Para criar as APIs REST.
- **Spring Boot Starter Data JPA**: Para integração com o PostgreSQL.
- **PostgreSQL Driver**: Para conectar a aplicação ao banco de dados PostgreSQL.
- **Springdoc OpenAPI**: Para a documentação automática da API via Swagger.

Para instalar todas as dependências, execute o comando:

```bash
mvn clean install
```

---

## Como Executar o Projeto

1. **Certifique-se de que o PostgreSQL está rodando na sua máquina.**

   Verifique se o banco de dados PostgreSQL está em execução na sua máquina.

2. **Crie o banco de dados no PostgreSQL.**

   No PostgreSQL, crie um banco de dados chamado `agenda-simulator-db`. Você pode fazer isso executando o comando SQL abaixo:

   ```sql
   CREATE DATABASE agenda-simulator-db;
   ```

3. **Ajuste as credenciais de banco de dados.**

   Caso necessário, altere as credenciais no arquivo `application.properties` para que correspondam às configurações do seu PostgreSQL.

4. **Execute a aplicação.**

   Após configurar o banco de dados e as credenciais, execute o seguinte comando para rodar a aplicação:

   ```bash
   mvn spring-boot:run
   ```

   A aplicação estará rodando em **http://localhost:8081**.

---

## Acessando a Aplicação

1. **Swagger UI**: Para testar a API diretamente pela interface visual do Swagger, acesse:

   ```plaintext
   http://localhost:8081/swagger-ui/index.html
   ```

   O Swagger UI fornecerá uma interface interativa para testar as APIs disponíveis na aplicação, como a criação de agendas, sessões de votação e a contagem de votos.

2. **Testando as APIs**: Você também pode usar ferramentas como **Postman** ou **CURL** para testar as APIs. O Swagger UI é uma forma prática de explorar e interagir com as APIs.

---