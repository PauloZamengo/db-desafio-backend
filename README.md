# Desafio Back-end DB

Api de desafio back-end em Java utilizando Springboot da empresa DB


## Documentação da API

#### Cadastro de novas pautas

```http
  POST /votacao/v1/pauta
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

```http
  GET /votacao/v1/pauta
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

```http
  POST votacao/v1/sessao
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

```http
  POST votacao/v1/voto
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

```http
  GET votacao/v1/voto/{id_pauta}
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

```http
  GET votacao/v1/user/{cpf}
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