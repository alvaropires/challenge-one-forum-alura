# Challenge ONE | Back End | Alura Forum 


![Captura de tela de 2023-07-13 16-37-04](https://github.com/alvaropires/challenge-one-forum-alura/assets/94912998/e9fa82b4-7d37-4e61-ba32-e7d13f4f78bf)




### Tecnologias utilizadas:

- [Intellij IDEA](https://www.jetbrains.com/pt-br/idea/)
- [MySql](https://www.mysql.com/)
- [Java](https://www.java.com/pt-BR/)
- [Spring Boot](https://spring.io/)
- [Spring Security](https://start.spring.io/)
- [Token JWT](https://jwt.io/)
- [OpenAPI](https://www.openapis.org/)
- [Flyway Migrations](https://flywaydb.org/)


## Descrição do projeto

Este projeto é uma API de um Fórum da Alura fictício com os dados persistidos em um banco de dados MySQL desenvolvido utilizando o Framework Spring Boot.

As tabelas do banco de dados seguem um padrão de colunas com ID(chave primária gerada de forma sequencial pelo banco), existe relacionamentos entre as tabelas e o seu desenvolvimento foi acompanhado utilizando ferramentas do Flyway Migrations. 

A segurança da API foi implementada utilizando o Spring Security, com autenticação via Token JWT e para documentação e teste da aplicação foi utilizada a biblioteca do OpenAPI, utilizando o Swagger. 

## Funcionalidades

:heavy_check_mark: Autenticação de usuário utilizando Token JWT e autorização de acesso a endpoints de acordo com o perfil do usuário

:heavy_check_mark: CRUD completo dos Cursos

:heavy_check_mark: CRUD completo dos Usuários

:heavy_check_mark: CRUD completo dos Tópicos

:heavy_check_mark: CRUD completo das Respostas

:heavy_check_mark: Possibilidade de moderadores alterarem o Status dos tópicos de acordo com o necessário



## SQL / Banco de Dados


Para este projeto foi utilizado o MySQL como Banco de Dados para persistência. As configurações de acesso podem ser alteradas no arquivo [application.properties](https://github.com/alvaropires/challenge-one-forum-alura/blob/repositorio-modelo/src/main/resources/application.properties). O desenvolvimento desta base de dados foi acompanhado e controlado a com auxílio da Biblioteca do Flyway, utilizando as [Migrations](https://github.com/alvaropires/challenge-one-forum-alura/tree/repositorio-modelo/src/main/resources/db/migration).

O  relacionamento das entidades na aplicação está disposto conforme o diagrama abaixo:

![Captura de tela de 2023-07-13 16-27-31](https://github.com/alvaropires/challenge-one-forum-alura/assets/94912998/aaee6f93-47d5-4964-b378-c8481072e831)




## Layout da aplicação



https://github.com/alvaropires/challenge-one-forum-alura/assets/94912998/320d2d1c-3eeb-4463-baa4-72c8da4fb075




## Como rodar a aplicação :arrow_forward:

Para rodar esta aplicação e testar diretamente em sua máquina você precisará seguir os seguintes passos:

- 1º - Clonar este repositório com o seguinte comando em seu terminal:
 
  ```
  git clone https://github.com/alvaropires/challenge-one-forum-alura.git
  ```

- 2º - Configurar o acesso ao seu banco de dados de preferência no arquivo [application.properties](https://github.com/alvaropires/challenge-one-forum-alura/blob/repositorio-modelo/src/main/resources/application.properties) e caso use um Banco de Dados diferente do MySQL você deverá configurar as dependências necessárias no arquivo [pom.xml](https://github.com/alvaropires/challenge-one-forum-alura/blob/repositorio-modelo/pom.xml).

- 3º - Crie sua própria branch e faça as alterações necessárias ao projeto.

- 4º - Copie e cole o link abaixo diretamente em seu navegador para acessar a página da OpenAPI com a documentação e acesso a sua API do Fórum Alura:

  ```
  http://localhost:8080/swagger-ui/index.html
  ```


---

## Desenvolvedor :octocat:

[<img src="https://avatars.githubusercontent.com/u/94912998?s=96&v=4" width=115><br><sub>Alvaro Pires Santos</sub>](https://github.com/alvaropires)


<a href="https://www.linkedin.com/in/alvaro-pires-santos/" target="_blank">
<img src="https://img.shields.io/badge/-LinkedIn-%230077B5?style=for-the-badge&logo=linkedin&logoColor=white" target="_blank"></a>

