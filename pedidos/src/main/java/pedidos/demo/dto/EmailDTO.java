package pedidos.demo.dto;

import lombok.Getter;
import lombok.Setter;
import pedidos.demo.model.FormaDePagamento;
import pedidos.demo.model.PaymentStatus;
import pedidos.demo.model.Pedido;
import pedidos.demo.model.PedidoCredito;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class EmailDTO {
    private String email;
    private String nome;
    private String cpf;
    private FormaDePagamento formaDePagamento;
    private int parcelas;
    private String descricaoPedido;
    private BigDecimal valor;
    private PaymentStatus paymentStatus;

    public EmailDTO(Pedido pedido, PedidoCredito credito) {
        this.email = pedido.getEmail();  // Obtém o email do Pedido
        this.nome = pedido.getNome();    // Obtém o nome do Pedido
        this.cpf = pedido.getCpf();      // Obtém o CPF do Pedido
        this.formaDePagamento = pedido.getFormaDePagamento();
        if(formaDePagamento == FormaDePagamento.CREDITO){
            this.parcelas = credito.getParcelas();
        }
        this.descricaoPedido = pedido.getDescricaoPedido();
        this.valor = pedido.getValor();
        this.paymentStatus = pedido.getPaymentStatus();
    }
    public EmailDTO(Pedido pedido) {
        this.email = pedido.getEmail();  // Obtém o email do Pedido
        this.nome = pedido.getNome();    // Obtém o nome do Pedido
        this.cpf = pedido.getCpf();      // Obtém o CPF do Pedido
        this.formaDePagamento = pedido.getFormaDePagamento();
        // Obtém o número de parcelas do Pedido
        this.descricaoPedido = pedido.getDescricaoPedido(); // Obtém a descrição do Pedido
        this.valor = pedido.getValor();
        this.paymentStatus = pedido.getPaymentStatus();
    }
}
