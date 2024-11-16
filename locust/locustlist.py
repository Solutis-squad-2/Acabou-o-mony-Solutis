from locust import HttpUser, task, between
import random
import string

class AccountTestUser(HttpUser):
    wait_time = between(1, 3)  # tempo de espera entre as requisições

    # Tarefa de GET para /account/list
    @task
    def get_account_list(self):
        headers = {
            "Authorization": "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0ZXNvbHV0aXM5QGdtYWlsLmNvbSIsImlzcyI6IjJGQSIsImV4cCI6MTczMTc4OTMzNiwidXVpZCI6IjYwNDYwNWE4LTc2YzUtNDkyZi05OTA5LWEwYWI4OWYxMmU1YiJ9.lVP6wU7ViZGQTXSsafesOtphoimHyTDsvQl8C9sfnRQ",  # Substitua com seu token
        }
        # Requisição GET para o endpoint /account/list
        response = self.client.get("/account/list", headers=headers)
        
        # Verifique a resposta do servidor (opcional)
        if response.status_code == 200:
            print("Sucesso ao obter lista de contas")
        else:
            print(f"Erro ao obter lista de contas. Status code: {response.status_code}")

    def on_start(self):
        self.client.verify = False
        """Esse método será chamado quando o usuário for iniciado"""
        print("Testando o GET /account/list com aumento progressivo de carga")

