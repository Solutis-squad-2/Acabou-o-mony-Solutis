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
       
        
        # Enviar a requisição para o endpoint de pagamento
        response = self.client.get("/pedidos", headers=headers)
