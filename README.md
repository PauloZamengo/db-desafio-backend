# Desafio Back-end DB

Api de desafio back-end em Java utilizando Springboot da empresa DB

## Stack
    - Java 11
    - Springboot
    - Springcloud
    - Postgres
    - Docker
    - Liquibase
    - JMeter
    - Postman

## Como executar o sistema

Para executar o sistema, basta abrir o terminal e executar o comando `docker-compose up` na raiz do projeto. Também é necessário ter o docker instalado na máquina.

**Observação**: O processo de buildar e subir a aplicação pode demorar uns minutos, devido aos artefatos que precisam ser baixados. Também é necessário ter o docker instalado.

## Framework
A aplicação foi desenvolvida utilizando a linguagem Java e o framework Spring-boot na versão 2.4.9

## Banco de dados

Para realização de versionamento do banco de dados foi utilizado a biblioteca Liquibase. Os arquivos de construção da estrutura de dados estão no diretório `src/resources/db/changelog/scripts.`

    - schema.sql: Constrói um schema de banco de dados chamado dbschema
    - pauta-sessao-voto.sql: Constrói as tabelas de pauta, sessão e voto, e a criação das sequences

## Modelagem e Estrutura de dados

A estrutura de dados foi elaborada da seguinte maneira:
* Pauta
* Sessao
* Voto

A pauta terá uma sessão e a pauta possuí vários votos.
Então foi pensado em uma tabela para o cadastro de pautas. 
Uma tabela para armazenar a sessão da pauta e gravar informações de abertura das sessões de votação.
Também teremos uma tabela que grava as informações sobre os votos, onde esses estarão vinculados a pauta (e essa que estará vinculada a sessão para verificar se a sessão ainda estará aberta ou não, permitindo ou não o registro dos votos.)
A tabela de voto também grava informações de um `id_associado`, que teoricamente estaria vinculado a uma tabela de associados. Porém como não estava no escopo do desafio, essa tabela de id_associado não foi criada.


## Estrutura de classes

As classes foram divididas por assunto dentro do pacote votacao. Cada assunto possuí sua respectiva classe, e estão divididos nos pacotes client, controller, entity, exception, repository e service.

## Documentação da API

#### Cadastro de novas pautas

```
  POST /votacao/v1/pautas
```

Deve ser enviado um método POST, com o body com a descrição da pauta.

```
{
    "descricao": "Essa pauta será sobre o que?"
}
```

| Campo            | Tipo     | Descrição                                    |
|:-----------------|:---------|:---------------------------------------------|
| `descricao`      | `string` | **Obrigatório**. Indica a descrição da pauta |

#### Listagem das pautas

```
  GET /votacao/v1/pautas
```

Esse método retorna a lista de pautas.
Exemplo Response:
```
[
    {
        "id_pauta": 1,
        "descricao": "A favor ou contra uma nova pauta?"
    },
    {
        "id_pauta": 10,
        "descricao": "Essa pauta será sobre o que?"
    }
]
```

#### Abrir Sessão de Votação

```
  POST votacao/v1/sessoes
```

Esse método tem o objetivo de abrir uma sessão de votação da pauta.
Deve ser enviado um método POST, com o body, onde é informado o id da pauta e o tempo que a sessão ficará em aberto.
```
{
    "id_pauta": 10,
    "tempo_abertura": 15
}
```


| Campo   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `id_pauta` | `int` | **Obrigatório**. Indica a pauta da sessão a ser aberta |
| `tempo_abertura` | `int` | Opcional. Indica o tempo de abertura que a pauta ficará aberta |

Response:

```
{
    "idSessao": 1,
    "pauta": {
        "idPauta": 10,
        "descricao": "Nova pauta"
    },
    "status": "ABERTA",
    "dataHoraAbertura": "2023-01-21T17:55:06.3482987",
    "tempoAbertura": 15
}
```

#### Votar

```
  POST votacao/v1/votos
```
Esse método tem o objetivo de incluir um voto do associado. Deve ser enviado um body

```
{
    "id_pauta": 10,
    "id_associado": 2,
    "valor": "SIM"
}
```

| Campo   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `id_pauta` | `int` | **Obrigatório**. Indica a pauta do voto |
| `id_associado` | `int` | **Obrigatório**. Indica o id do associado que esta votando|
| `valor` | `string` | **Obrigatório**. Indica se o voto é SIM ou NAO |

#### Contagem de votos

```
  GET votacao/v1/votos/{id_pauta}
```
Esse método retorna a contagem total dos votos

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `id_pauta` | `int` | **Obrigatório**. Indica a pauta da contagem de votos |

Response:

```
{
    "votosTotal": 20,
    "votosSim": 12,
    "votosNao": 8,
    "idPauta": 10
}
```

#### Validar se o associado está apto a votar

```
  GET votacao/v1/users/{cpf}
```
Esse método retorna se um associado está apto a votar ou não

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `cpf` | `string` | **Obrigatório**. Indica o cpf do associado |

Response:

```
{
    "status": "ABLE_TO_VOTE"
}
```

## Collection Postman
- Foi criado uma collection no Postman para chamadas dos serviços propostos no desafio. A collection se encontra no link a seguir e poderá ser importada diretamente no postman.
- https://drive.google.com/file/d/1y5Q7eFmXx75vpq60006iou9E0VW4EiJs/view?usp=sharing

## Testes de performance

Para os testes de performance foi utilizado a ferramenta Apache JMeter, onde foi configurado um plano de testes para execução das rotas, simulando um grupo de usuários. No caso desse projeto, foi criado um grupo de usuários com 10 usuários virtuais, com o tempo de incialização em 10 segundos, ou seja, irá levar 10 segundos para que os 10 usuários estejam fazendo requisições, em um teste com duração total de 190 segundos.

### Como executar os testes de performance
- Para execução dos testes de performance é necessário fazer o download do JMeter no link a seguir
https://jmeter.apache.org/download_jmeter.cgi

- Assim que o download for concluído, o arquivo baixado deverá se descompactado. Entre na pasta `bin` e execute o arquivo ApacheJMeter.jar. 
- Entre no seguinte caminho Arquivo -> Abrir. E seleciona o arquivo `.jmx` que está disponível no link a seguir. 
- https://drive.google.com/file/d/1y5Q7eFmXx75vpq60006iou9E0VW4EiJs/view?usp=sharing
