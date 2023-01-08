# Conexa Desafio 2
[![NPM](https://img.shields.io/npm/l/react)](https://github.com/miguelmoraisdev/desafioconexa2/blob/master/LICENCE) 

# Sobre o projeto
O Conexa Desafio 2 é uma API-REST que usa FeignClient para buscar na [DesafioConexa1](https://github.com/miguelmoraisdev/desafioconexa1) os filmes de Star Wars que tenham como personagem envolvido Luke Skywalker, salvá-los no banco de dados H2 juntamente com o novo filme a ser inserido.

A aplicação não permite que um filme já existente no banco seja adicionado novamente. O mesmo serve para os filmes que são obitidos a partir da API ConexaDesafio1. Se eles já foram adicionados ao banco, a aplicação manda uma exception explicando que o filme já foi adicionado.

Quando a apilcação [DesafioConexa1](https://github.com/miguelmoraisdev/desafioconexa1) não está respondendo a aplicação lança uma exception ao front com a mensagem "Time out for the client".

Após adicionar os filmes no banco, a lista de filmes é impressa no console da aplicação.

Para esse projeto foram desenvolvidos testes unitários das camadas de service e controller.

Foi utilizado a ferramenta OpenAPI (Swagger) para documentação dos endpoints da aplicaçação:
http://localhost:8080/swagger-ui/index.html

Acesso ao banco de dados H2 é feito pelo link:
http://localhost:8080/h2

> Status do Projeto: :heavy_check_mark: :warning: (concluido)

# Funcionalidades

:heavy_check_mark: Adicionar filme na lista de filmes que tem Luke Skywalker como personagem envolvido. O formato do corpo da requisção deve seguir esse padrão:

```bash
{
    "title": "A New Hope",
    "episode_id": "4",
    "director": "George Lucas",
    "release_date": "25/05/1977"
}
```

# Tecnologias utilizadas
- Java 17
- Spring Boot
- Maven
- Feign Client
- OpenApi (Swagger)
- Testes (Junit e Mockito)

# Pré-requisitos

:warning: [Java-17]

:warning: [Intellij IDEA]

:warning: [Spring Boot]

:warning: [Estar rodando a aplicação [DesafioConexa1](https://github.com/miguelmoraisdev/desafioconexa1)]

# Como rodar a aplicação :arrow_forward:

```bash
# clonar repositório
# HTTPS
git clone https://github.com/miguelmoraisdev/desafioconexa2.git

# SSH
git clone git@github.com:miguelmoraisdev/desafioconexa2.git

# entrar na pasta do projeto back end
cd desafioconexa2

# executar o projeto
./mvnw spring-boot:run
```
Você também pode abrir o projeto por uma IDE

Apoś baixar o projeto, você pode abrir o Intellij IDEA. Aós abri-lo, clique em:
- File -> Open 

- Procure o local onde está o projeto e o selecione.

- Por fim click em OK.

- Configure a IDE para rodar com o Java 17 e a classe principal do programa. 

# Autor

Miguel Augusto de Morais Junior

https://www.linkedin.com/in/miguel-morais-04a9ab1b0/

