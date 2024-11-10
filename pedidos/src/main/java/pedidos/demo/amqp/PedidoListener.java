package pedidos.demo.amqp;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import pedidos.demo.dto.ConfirmPaymentDTO;
import pedidos.demo.dto.PaymentDTO;
import pedidos.demo.dto.PedidosDTO;
import pedidos.demo.model.FormaDePagamento;
import pedidos.demo.model.Pedido;
import pedidos.demo.service.PedidosService;

@Component
public class PedidoListener {


    @Autowired
    private PedidosService pedidosService;


    @Retryable(
            value = {Exception.class},
            maxAttempts = 5,
            backoff = @Backoff(delay = 5000)
    )
    @RabbitListener(queues = "fila.confirmacao.payment")
    public void processarConfirmacaoPagamento(ConfirmPaymentDTO paymentDTO){
        try {
            if (paymentDTO == null || paymentDTO.getPaymentId() == null) {
                System.out.println("Mensagem do pagamento inválida recebida. Dados ausentes!");
                return;
            }

            ResponseEntity<PedidosDTO> pedido = pedidosService.atualizarRespostaPedido(paymentDTO);
            Pedido p = pedidosService.findPedido(paymentDTO.getPaymentId());
            pedidosService.enviarPedidoParaEmail(p);

        }catch(Exception e){
            System.err.println("Erro ao processar confirmação de pagamento: " + e.getMessage());
        }


    }

}
