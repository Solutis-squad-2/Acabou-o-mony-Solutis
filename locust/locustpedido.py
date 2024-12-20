from locust import HttpUser, task, between

class PaymentTest(HttpUser):
    wait_time = between(1, 3)
    def on_start(self):
            # Ignorar erro de verificação de certificado SSL
            self.client.verify = False
    # Insira o seu token de autorização aqui
    token = ""

    @task
    def send_payment_request(self):
        headers = {
            "Content-Type": "application/json",
            "Authorization": f"Bearer {self.token}"
        }
        payload = {
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
        
        # Enviar a requisição para o endpoint de pagamento
        response = self.client.post("/pedidos/cadastro", json=payload, headers=headers)

        if response.status_code == 200:
            print("Requisição enviada com sucesso")
        else:
            print(f"Erro: {response.status_code} - {response.text}")
