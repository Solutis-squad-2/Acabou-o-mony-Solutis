from locust import HttpUser, task, between

class PaymentTest(HttpUser):
    wait_time = between(1, 3)
    def on_start(self):
            # Ignorar erro de verificação de certificado SSL
            self.client.verify = False
    # Insira o seu token de autorização aqui
    token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0ZXNvbHV0aXM5QGdtYWlsLmNvbSIsImlzcyI6IjJGQSIsImV4cCI6MTczMTg2Njc5OCwidXVpZCI6IjYwNDYwNWE4LTc2YzUtNDkyZi05OTA5LWEwYWI4OWYxMmU1YiJ9.DJpmllDPYN8s6Hbe1bAXi85mlqTEW4Ah8aB6iTPvT-Q"

    @task
    def send_payment_request(self):
        headers = {
            "Content-Type": "application/json",
            "Authorization": f"Bearer {self.token}"
        }
       
        
        # Enviar a requisição para o endpoint de pagamento
        response = self.client.get("/pedidos", headers=headers)
