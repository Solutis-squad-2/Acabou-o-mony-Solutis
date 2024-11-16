import random
import time
from locust import HttpUser, task, between

class MicroserviceUser(HttpUser):
    wait_time = between(1, 3)  # Tempo de espera entre as requisições

    def on_start(self):
        """Método chamado quando o usuário inicia sua sessão"""
        # Ignorar erro de verificação de certificado SSL
        self.client.verify = False
        self.email_counter = 0  # Inicializa o contador de emails

    def gerar_cpf(self):
        """Gera um CPF aleatório de 11 dígitos."""
        return str(random.randint(10000000000, 99999999999))

    def gerar_telefone(self):
        """Gera um número de telefone aleatório no formato DDD + celular (75998995544)."""
        ddd = random.randint(10, 99)  # Gera um DDD válido
        numero = random.randint(900000000, 999999999)  # Gera um celular no formato 9XXXXXXXX
        return f"{ddd}{numero}"

    @task
    def test_account_service(self):
        """Executa o teste de registro de conta no microserviço"""
        timestamp = int(time.time() * 1000)  # Multiplica para garantir mais precisão
        cpf = self.gerar_cpf()
        telefone = self.gerar_telefone() 
        senha = "senha123"

        # Gera um email único baseado no timestamp e um número aleatório
        email = f"usuario{timestamp}_{random.randint(1000, 9999)}@gmail.com"
        # O contador de emails foi removido, pois agora o email é único pela combinação do timestamp e número aleatório

        # Dados JSON para a requisição
        json_data = {
            "email": email,
            "senha": senha,
            "nome": "Nome Teste",
            "telefone": telefone,
            "cpf": cpf
        }

        # Imprime o JSON para verificar os dados
        print("Enviando JSON:", json_data)

        # Envia a requisição POST
        response = self.client.post("/account/register", json=json_data)
        
        # Imprime o status e a resposta para verificar se a requisição foi bem-sucedida
        print("Status Code:", response.status_code)
        print("Response Text:", response.text)
