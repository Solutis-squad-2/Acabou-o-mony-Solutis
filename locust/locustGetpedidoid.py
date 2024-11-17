from locust import HttpUser, task, between

class PaymentTest(HttpUser):
    wait_time = between(1, 3)

    def on_start(self):
        # Ignorar erro de verificação de certificado SSL
        self.client.verify = False

    # Insira o seu token de autorização aqui
    token = ""

    @task
    def get_pedido_by_id(self):
        pedido_id = 71000  # Substitua pelo ID que deseja buscar
        headers = {
            "Content-Type": "application/json",
            "Authorization": f"Bearer {self.token}"
        }

        # Enviar a requisição para buscar o pedido pelo ID
        response = self.client.get(f"/pedidos/{pedido_id}", headers=headers)

        # Logar a resposta para debug
        if response.status_code == 200:
            print(f"Pedido encontrado: {response.json()}")
        else:
            print(f"Erro ao buscar pedido: {response.status_code} - {response.text}")
