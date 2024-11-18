import random
import time
from locust import HttpUser, task, between

class MicroserviceUser(HttpUser):
    wait_time = between(1, 3)  # Tempo de espera entre as requisições

    def on_start(self):
        # Ignorar erro de verificação de certificado SSL
        self.client.verify = False

    @task
    def test_new_path(self):
        # Dados JSON para o novo caminho
        json_data = {
            "codigo": "Colocar o codigo"
        }

        # Envia a requisição POST para o endpoint
        response = self.client.post("/account/confirmar-codigo", json=json_data)  # Substitua "/new/endpoint" pelo caminho correto
        
        # Imprime o status e a resposta
        print("Status Code:", response.status_code)
        print("Response Text:", response.text)
