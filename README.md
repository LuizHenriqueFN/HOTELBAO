# HOTELBAO

## Descrição do Projeto

Este projeto consiste na criação do backend de um protótipo funcional para o sistema HOTELBAO, responsável por registrar estadias de clientes em um hotel fictício. O sistema deve gerenciar dados de clientes, quartos e estadias, além de fornecer informações como valor total da estadia por cliente, relatórios diversos, estadia mais cara e mais barata por cliente, e emissão de "cupom fiscal".

O foco principal é o desenvolvimento do backend, que poderá ser testado via ferramentas como Postman ou Swagger.

## Requisitos

*   Registro de clientes (código, nome, e-mail, senha, login, celular).
*   Registro de quartos (código, descrição, valor, url da imagem).
*   Registro de estadias (associando cliente, quarto e data). As estadias são sempre por 1 dia.
*   Disponibilização de informações ao usuário:
    *   Valor total da estadia por cliente.
    *   Relatório dos dados cadastrados (clientes, quartos, estadias).
    *   Estadia mais cara por cliente.
    *   Estadia mais barata por cliente.
    *   Emissão de "cupom fiscal".
*   Login e senha ("jj" para ambos) para acesso ao programa.
*   Implementação de um menu de opções para interação com o sistema.

## Tecnologias e Dependências

Este projeto está sendo desenvolvido utilizando Java com o framework Spring Boot. As seguintes dependências foram selecionadas:

*   **Spring Web:** Essencial para a criação dos endpoints da API REST, manipulação de requisições HTTP e definição das respostas do servidor.
*   **Spring Boot DevTools:** Ferramenta que acelera o ciclo de desenvolvimento, permitindo reinícios rápidos da aplicação e outras funcionalidades úteis durante a fase de desenvolvimento.
*   **Lombok:** Biblioteca que reduz a quantidade de código boilerplate em classes Java, como a geração automática de getters, setters, construtores, `equals`, `hashCode` e `toString`.

Além dessas, para a completude do projeto, serão necessárias outras dependências, como:

*   **Dependência de Banco de Dados:** Para persistência dos dados. Será utilizada uma dependência como JPA (com Hibernate, caso use Spring Data JPA) ou um driver específico para o banco de dados escolhido (ex: PostgreSQL, MySQL, etc.).
*   **Dependências de Teste:** Para garantir a qualidade do código, serão utilizadas bibliotecas de teste como JUnit e Mockito.
*   **Swagger/OpenAPI:** Para a documentação automática da API, facilitando o entendimento e o uso dos endpoints desenvolvidos.

## Configuração do Ambiente

Para rodar este projeto, você precisará ter instalado:

*   Java Development Kit (JDK)
*   Maven ou Gradle (para gerenciamento de dependências)
*   Um ambiente de desenvolvimento integrado (IDE) de sua preferência (ex: IntelliJ IDEA, Eclipse, VS Code)

Clone o repositório do projeto e importe-o em sua IDE. As dependências serão baixadas automaticamente pelo Maven/Gradle.

## Como Executar

1.  Certifique-se de que o banco de dados configurado está em execução.
2.  Execute a classe principal da aplicação Spring Boot em sua IDE.
3.  O backend estará disponível em um endereço local (geralmente `http://localhost:8080`, a menos que configurado de outra forma).

## Testes da API

Você pode testar os endpoints da API utilizando ferramentas como:

*   **Postman:** Uma aplicação popular para testar APIs.
*   **Swagger:** Uma ferramenta para documentar e testar APIs. A documentação da API estará disponível em um endpoint específico (geralmente `/swagger-ui.html` ou similar) após a integração do Swagger ao projeto.

## Contribuição

Este trabalho é em dupla. A contribuição deve ser feita através do Git e GitHub, garantindo uma boa organização do código, indentação e a utilização de comentários para melhorar a legibilidade.

## Autor(es)

Luiz Henrique

Danilo Araújo