package pedidos.demo.amqp;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import pedidos.demo.dto.PaymentDTO;
import pedidos.demo.dto.PedidosDTO;
import pedidos.demo.service.PedidosService;

@Component
public class PedidoListener {


    @Autowired
    private PedidosService pedidosService;

    @RabbitListener(queues = "fila.confirmacao.payment")
    public void processarConfirmacaoPagamento(PaymentDTO paymentDTO){
        try {
            if (paymentDTO == null || paymentDTO.getId() == null) {
                System.out.println("Mensagem do pagamento inválida recebida. Dados ausentes!");
                return;
            }

            ResponseEntity<PedidosDTO> pedido = pedidosService.atualizarRespostaPedido(
                    paymentDTO.getId(),
                    paymentDTO.getStatusPagamento(),
                    paymentDTO.getDataProcessamento());
            if (pedido != null && pedido.getBody() != null) {
                pedidosService.enviarPedidoParaEmail(pedido.getBody());

            } else {
                System.out.println("Pedido não encontrado ou falha ao atualizar o pedido");
            }
        }catch(Exception e){
            System.err.println("Erro ao processar confirmação de pagamento: " + e.getMessage());
        }


    }

}
