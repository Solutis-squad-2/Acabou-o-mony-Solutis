# Acabou o Mony - Solutis


## Sobre o Projeto

O Acabou o Mony é uma plataforma de pagamento e gestão de transações financeiras no qual o objetivo do projeto é criar um sistema altamente escalável, seguro e capaz de processar grandes volumes de transações. A plataforma é composta por múltiplos serviços independentes, que comunicam entre si por meio de mensageria e oferecem funcionalidades como gestão de pedidos, pagamentos e segurança.

## Tecnologias Utilizadas
- **Java 21**
- **Spring Boot**
- **Docker**
- **PostgreSQL**
- **Redis**
- **RabbitMQ**
- **Locust**

## Arquitetura do Projeto
A arquitetura do Acabou o Mony é baseada em uma estrutura de microsserviços, onde cada serviço é responsável por uma parte específica do processo de transação financeira. A escolha por microsserviços foi feita para garantir escalabilidade, manutenibilidade e isolamento entre as diferentes partes do sistema.
### Fluxo de Funcionamento
1. Cadastro/Login: O usuário se cadastra ou faz login no sistema através da interface, que comunica com o Account Service. Após a validação das credenciais, o Security autentica o usuário com um código enviado por email e libera o acesso aos demais serviços.
2. Criação de Pedido: O usuário cria um pedido através da interface, o que aciona o Order Service para gerenciar os dados do pedido. O serviço cria a transação necessária e a envia ao Payment Service.
3. Pagamento: O Payment Service processa o pagamento do pedido, interage com o sistema de pagamento e retorna o status da transação para o sistema.
4. Email: Uma vez que o pagamento é concluído, o Email Service envia uma notificação ao usuário sobre os dados de seu pedido.

## Como Executar o Projeto
### 1. Clone o repositório
```
https://github.com/Solutis-squad-2/Acabou-o-mony-Solutis.git
```

### 2. Instale as dependências do projeto
```
mvn install
```

### 3. Configuração das Ferramentas Necessárias
**PostgreSQL:** Antes de rodar, crie os seguintes bancos de dados:

    account_database, pedido_database, payment_database

**Redis:** Deve estar configurado para cache e armazenamento temporário.

**RabbitMQ:** A fila de mensagens deve estar configurada para comunicação entre os microsserviços.

### 4. Configuração de Certificado SSL no Java
Para validar o certificado SSL no Java, utilize o comando keytool conforme o exemplo abaixo:
``` 
keytool -import -alias mycert -file "camiho-do-certificado\mycert.crt" -keystore "caminho-para-o-cacerts\lib\security\cacerts" -storepass changeit
```

## Acessar as APIs
Com todos os serviços rodando, você pode acessar as APIs que foram implementadas.
### Fazer Cadastro
Para cadastrar um novo usuário, envie o seguinte JSON:
```
{
  "email": "testesolutis9@gmail.com",
  "senha": "123",
  "nome": "Pedro",
  "telefone": "75998964476",
  "cpf": "11111111111"
}
```

### Fazer Login
Para realizar o login de um usuário, envie o seguinte JSON:
```
{
  "email": "testesolutis9@gmail.com",
  "password": "123"
}
```

### Realizar Pedido
Para realizar um pedido, envie o seguinte JSON no corpo da requisição:
```
{
    "nome": "José Tiago Oliveira",
    "cpf": "09703327427",
    "telefone": "987654321",
    "descricaoPedido": "Pedido de teste",
    "valor": 200.00,
    "formaDePagamento": "CREDITO",
    "numeroCartao": "9876543210123450",
    "codigoCartao": "966",
    "parcelas": 5
}
```

## Teste de Carga
Para realizar o teste de carga, siga os passos abaixo:
### Instale o Locust 
Certifique-se de ter o Python instalado na sua máquina. Em seguida, instale o Locust com:
```
pip install locust
```
### Execute o Locust
Navegue até o diretório do projeto e execute o comando:
```
locust -f nome-do-arquivo-com-script.py
```
Isso iniciará a interface do Locust para testar a carga do sistema.

## Resultados dos Testes de Carga
Os resultados dos testes de carga realizados com o **Locust** podem ser acessados nos links abaixo:

1. **Confirmação de Código**
- [Visualizar Resultado](https://solutis-squad-2.github.io/Acabou-o-mony-Solutis/Resultados%20teste%20de%20carga/Solicita%C3%A7%C3%A3o%20de%20confirmar%20codigo.html)

2. **Solicitação de Lista de Usuários**
- [Visualizar Resultado](https://solutis-squad-2.github.io/Acabou-o-mony-Solutis/Resultados%20teste%20de%20carga/Solicita%C3%A7%C3%A3o%20de%20lista%20de%20usuarios.html)

3. **Solicitação de Login**
- [Visualizar Resultado](https://solutis-squad-2.github.io/Acabou-o-mony-Solutis/Resultados%20teste%20de%20carga/Solicita%C3%A7%C3%A3o%20de%20login.html)

4. **Solicitação de Pedido**
- [Visualizar Resultado](https://solutis-squad-2.github.io/Acabou-o-mony-Solutis/Resultados%20teste%20de%20carga/Solicita%C3%A7%C3%A3o%20de%20pedidos.html)

5. **Solicitação de Registro**
- [Visualizar Resultado](https://solutis-squad-2.github.io/Acabou-o-mony-Solutis/Resultados%20teste%20de%20carga/Solicita%C3%A7%C3%A3o%20de%20registro.html)

6. **Solicitação de Todos os Pedidos**
- [Visualizar Resultado](https://solutis-squad-2.github.io/Acabou-o-mony-Solutis/Resultados%20teste%20de%20carga/Solicita%C3%A7%C3%A3o%20de%20todos%20pedidos.html)

7. **Teste de Pedido por ID**
- [Visualizar Resultado](https://solutis-squad-2.github.io/Acabou-o-mony-Solutis/Resultados%20teste%20de%20carga/Solicita%C3%A7%C3%A3o%20pedidos%20por%20id.html)

## Desenvolvimento
Durante o desenvolvimento, adotamos uma abordagem ágil e iterativa, com foco na entrega de funcionalidades de maneira contínua e eficiente. Os serviços foram cuidadosamente planejados e implementados para garantir que cada componente fosse modular, com o objetivo de facilitar a manutenção e a escalabilidade do sistema. A comunicação entre os microsserviços é feita de forma assíncrona, o que permite que as transações sejam processadas de maneira eficiente sem sobrecarregar os recursos do sistema.

### Membros da Equipe
- **Pedro Rocha** - [GitHub](https://github.com/Pedro-E-S-R)
    - Implementação do Account Service e Email Service.
    - Configuração de segurança, incluindo autenticação e autorização de usuários.
    - Configuração do Gateway e Server para a comunicação entre microsserviços.
  
- **Oscar de Brito** - [GitHub](https://github.com/OscarDeBrito)
    - Execução e monitoramento dos testes de carga usando Locust.
    - Análise de desempenho e otimização dos serviços sob alta demanda.

- **Fábio Macedo** - [GitHub](https://github.com/fabio-macedo)
    - Desenvolvimento do Order Service, responsável pela criação, consulta e gestão de pedidos.
    - Integração do fluxo de pedidos com o serviço de pagamento.

- **Gabriel Pedro** - [GitHub](https://github.com/devGabrielPedro)
  - Implementação do Payment Service, com lógica de processamento de pagamentos.
  - Implementação de fluxos de validação e segurança relacionados ao processamento de pagamentos.
