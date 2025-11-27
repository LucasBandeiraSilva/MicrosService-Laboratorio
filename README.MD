# 🧪 HealthFlow – Plataforma de Exames Laboratoriais com Microsserviços

Bem-vindo ao **HealthFlow**, um ecossistema de microsserviços criado para simular o fluxo completo de um laboratório de exames: desde o cadastro do cliente, geração da coleta, faturamento, publicação da nota fiscal e finalização do resultado.

A arquitetura é totalmente distribuída, com comunicação assíncrona via **Kafka**, armazenamento de notas fiscais via **MinIO**, descoberta de serviços com **Eureka**, roteamento inteligente com **Cloud Gateway** e persistência em **MySQL**.

Prepare-se: **este projeto foi feito para despertar sua curiosidade e vontade de explorar!** 🚀💉✨

---

## 🏗️ Arquitetura do Projeto

Abaixo está uma visão geral da arquitetura do ecossistema HealthFlow, incluindo microsserviços, bancos de dados, comunicação via RabbitMQ e fluxo entre os domínios:

![Arquitetura do Projeto](./docs/Diagrama-MsLab)


## 🏗️ Arquitetura Geral

A plataforma é composta pelos seguintes microsserviços:

### 🔹 **clientes-service**

Responsável pelo cadastro e gerenciamento de clientes do laboratório.

### 🔹 **produto-service**

Cadastro e consulta dos exames disponíveis (ex.: Colesterol, TGP, etc.).

### 🔹 **coleta-service**

Gera pedidos de exame, consolida itens, calcula valores, processa pagamentos, e envia mensagens para o Kafka.

### 🔹 **faturamento-service**

Recebe pedidos do Kafka, muda o status para **EM_ANALISE**, gera nota fiscal via MinIO e publica novamente no Kafka.

### 🔹 **resultados-service**

Atualiza o status para **PRONTO** e gera o código de acompanhamento do paciente.

### 🔹 **cloudGateway**

API Gateway responsável pelo roteamento e balanceamento.

### 🔹 **eurekaServer**

Service discovery para registro automático de microsserviços.

### 🔹 **serviços**

Pasta contendo os arquivos Docker Compose para rodar banco, bucket e broker.

---

## 🗄️ Modelos das Entidades

### 👤 **Clientes**

Representa o paciente que realiza os exames.

* Nome, CPF, nascimento, telefone, e-mail.
* Validações completas usando Bean Validation.
* Atributo `ativo` é setado automaticamente ao cadastrar.

### 🧪 **Coleta**

Representa o pedido de exames.

* Código do cliente
* Data do pedido
* Total
* Dados de pagamento
* Lista de itens
* Status do exame
* URL da nota fiscal
* Código de acompanhamento

### 🔹 **ItemColeta**

Detalha os exames associados à coleta.

* Código do produto
* Quantidade
* Valor unitário

### 📦 **Produto**

Representa os exames disponíveis.

* Nome
* Valor unitário
* Atributo `ativo` setado automaticamente.

---

## 📬 Fluxo de Funcionamento da Plataforma

### 1️⃣ **Cliente realiza cadastro**

### 2️⃣ **Pedido de coleta é criado com itens e forma de pagamento**

### 3️⃣ **Coleta publica "pedido confirmado" no Kafka**

### 4️⃣ **Faturamento recebe e gera nota fiscal no MinIO**

### 5️⃣ **Nota fiscal é publicada novamente no Kafka**

### 6️⃣ **Resultados recebe e finaliza o exame**

Esse pipeline torna o sistema completamente desacoplado, escalável e resiliente.

---

# 🧪 Endpoints para Testes

A seguir, exemplos reais para testar localmente.

---

# 👤 Clientes (exemplos usando "**Bucky Barnes — O Soldado Invernal**")

### ➕ **Cadastrar Cliente**

```json
{
  "nome": "Bucky Barnes",
  "dataNascimento": "04/03/1917",
  "cpf": "123.123.123-87",
  "telefone": "(47) 94567-8901",
  "email": "winter.soldier@avengers.com"
}
```

### 🔍 Obter por ID

`GET http://localhost:8080/clientes/5`

### 🗑️ Deletar

`DELETE http://localhost:8080/clientes/1`

### ✏️ Atualizar

`PUT http://localhost:8081/clientes/1`

```json
{
  "nome": "James Buchanan Barnes",
  "dataNascimento": "04/03/1917",
  "cpf": "123.123.123-87",
  "telefone": "(47) 97777-7777",
  "email": "bucky.barnes@avengers.com"
}
```

---

# 📦 Produtos

### ➕ Cadastrar

```json
{
  "nome": "TGP",
  "valorUnitario": 14.35
}
```

### 🔍 Obter por ID

`GET http://localhost:8083/produtos/2`

### 🗑️ Deletar

`DELETE http://localhost:8083/produtos/3`

### 📋 Listar todos

`GET http://localhost:8083/produtos`

### ✏️ Atualizar

`PUT http://localhost:8083/produtos/1`

```json
{
  "nome": "Colesterol",
  "valorUnitario": 22.99
}
```

---

# 🧬 Coleta

### ➕ Criar Coleta

```json
{
  "codigoCliente": 2,
  "dadosPagamento": {
    "tipoPagamento": "PIX",
    "dados": 25654345675432
  },
  "itens": [
    { "codigoProduto": 2, "quantidade": 1, "valorUnitario": 10.00 },
    { "codigoProduto": 4, "quantidade": 1, "valorUnitario": 18.90 },
    { "codigoProduto": 5, "quantidade": 1, "valorUnitario": 22.99 }
  ]
}
```

### 🔁 Simular Callback do Banco

`POST http://localhost:8080/coletas/callback-pagamentos`

```json
{
  "codigo": 1,
  "chavePagamento":"a3fe5122-6fce-49c9-b2de-9e4f2bd7313e",
  "status": true,
  "observacoes": ""
}
```

### 🔁 Novo Pagamento (erro anterior)

```json
{
  "codigoPedido": 2,
  "tipoPagamento": "DEBITO",
  "dados": 25654345675432
}
```

### 🔍 Obter por ID

`GET http://localhost:8080/coletas/1`

---

# 🧾 Faturamento

### 📤 Upload da Nota Fiscal

`POST http://localhost:8080/bucket`

**form-data:**

* key: `file`
* value: arquivo.pdf / jpg / png

### 📄 Atualizar Nome da Nota

`POST http://localhost:8080/bucket?filename=NotaFiscal_Coleta_1.pdf`

---

# 🧱 Docker – Infraestrutura Completa

Na pasta **serviços/** você encontrará os arquivos:

### 🔹 **database.yml** – MySQL 8

### 🔹 **bucket.yml** – MinIO

### 🔹 **broker.yml** – Kafka + Zookeeper + Kafka UI

Cada arquivo foi pensado para ser simples, direto e ideal para desenvolvimento local.

---

# 🚀 Como Rodar o Projeto

1. Subir MySQL:

```bash
docker compose -f serviços/database.yml up -d
```

2. Subir MinIO:

```bash
docker compose -f serviços/bucket.yml up -d
```

3. Subir Kafka:

```bash
docker compose -f serviços/broker.yml up -d
```

4. Rodar Eureka
5. Rodar Cloud Gateway
6. Rodar microsserviços na ordem desejada

---

# 🎯 Objetivo do Projeto

O HealthFlow foi criado para demonstrar:

* Microsserviços com comunicação assíncrona
* Integração com Kafka
* Upload de arquivos com MinIO
* Balanceamento de carga com Gateway
* Service discovery com Eureka
* Boas práticas em arquitetura limpa e validações

Este projeto foi feito **para ser explorado, quebrado, modificado, testado e expandido!**

Se você gosta de arquitetura real, escalável, com mensageria, arquivos e múltiplos serviços, o HealthFlow vai te prender.

---

# 🦾 Contribua

Sinta-se livre para clonar, testar, enviar PRs, reportar issues, ou até adaptar para novos cenários.

O laboratório é seu agora. 🧬🧪🔥

---

# 🛡️ Autor

Desenvolvido por **Lucas Bandeira da Silva**.

Se precisar de ajuda, ajustes ou quiser criar os READMEs individuais dos microsserviços, é só chamar.
