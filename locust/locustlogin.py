import random
import time
from locust import HttpUser, task, between

# Contador global para garantir emails únicos
email_counter = 0

class MicroserviceUser(HttpUser):
    wait_time = between(1, 3)  # Tempo de espera entre as requisições

    def on_start(self):
        # Ignorar erro de verificação de certificado SSL
        self.client.verify = False

    @task
    def test_account_service(self):
        # Dados JSON para a requisição (corrigido)
        json_data = {
            "email": "testesolutis9@gmail.com",
            "password": "123"
        }

        # Imprime o JSON para verificar os dados
        print("Enviando JSON:", json_data)

        # Envia a requisição POST
        response = self.client.post("/account/login", json=json_data)
        
        # Imprime o status e a resposta para verificar se a requisição foi bem-sucedida
        print("Status Code:", response.status_code)
        print("Response Text:", response.text)
