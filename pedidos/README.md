Links para uso da API de serviços

Busca Pedidos:<BR> 
      MÉTODO GET: http://localhost:8080/pedidos<BR>
Busca por ID:<BR>
      MÉTODO GET: http://localhost:8080/pedidos/5<BR>
Busca por CPF:<BR>
      MÉTODO POST: http://localhost:8080/pedidos/cpf<BR>

--------  MUDA A "FORMA DE PAGAMENTO" NO JSON PARA OS CADASTROS  -----------      
Cadastro pedido Débito:<BR>
      MÉTODO POST: http://localhost:8080/pedidos/cadastro<BR>
Cadastro pedido Crédito:<BR>
      MÉTODO POST: http://localhost:8080/pedidos/cadastro<BR>
Cadastro pedido PIX:<BR>
      MÉTODO POST: http://localhost:8080/pedidos/cadastro<BR>
Apagar por ID:<BR>
      MÉTODO DEL: http://localhost:8080/pedidos/apagar/4<BR>


## JSONs para uso no serviço de pedidos


### Cadastro

-> Cadastro pedido débito<BR>
{<BR>
    "nome": "João Silva",<BR>
    "cpf": "12345678901",<BR>
    "email": "joao.silva@email.com",<BR>
    "telefone": "123456789",<BR>
    "descricaoPedido": "Pedido de teste",<BR>
    "valor": 100.00,<BR>
    "formaDePagamento": "DEBITO",<BR>
    "numeroCartao": "1234567890123456",<BR>
    "codigoCartao": "123"<BR>
}<BR><BR>

-> Cadastro pedido crédito<BR>
{<BR>
    "nome": "Maria Oliveira",<BR>
    "cpf": "09703327427",<BR>
    "email": "maria.oliveira@email.com",<BR>
    "telefone": "987654321",<BR>
    "descricaoPedido": "Pedido de teste",<BR>
    "valor": 200.00,<BR>
    "formaDePagamento": "CREDITO",<BR>
    "numeroCartao": "9876543210123456",<BR>
    "codigoCartao": "456",<BR>
    "parcelas": 3<BR>
}<BR><BR>

-> Cadastro pedido pix<BR>
{<BR>
    "nome": "Tiago Jorge",<BR>
    "cpf": "09703327427",<BR>
    "email": "carlos.almeida@email.com",<BR>
    "telefone": "543216789",<BR>
    "descricaoPedido": "Pedido de teste",<BR>
    "valor": 457.00,<BR>
    "formaDePagamento": "PIX"<BR>
}<BR><BR>

### Busca por CPF<BR>
{<BR>
    "cpf":"09703327427"<BR>
}<BR>
