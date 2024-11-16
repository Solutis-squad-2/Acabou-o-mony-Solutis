package pedidos.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pedidos.demo.model.FormaDePagamento;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class PaymentDTO {
    private Long orderid;
    private String cpf;
    private FormaDePagamento formaDePagamento;
    private String numeroCartao;
    private String codigoCartao;
    private int parcelas;
    private BigDecimal valor;
    private LocalDateTime dataCadastro;


    public PaymentDTO(PedidosUserDTO pedidos) {
        this.orderid = pedidos.getId();
        this.cpf = pedidos.getCpf();
        this.formaDePagamento = pedidos.getFormaDePagamento();
        this.numeroCartao = pedidos.getNumeroCartao();
        this.codigoCartao = pedidos.getCodigoCartao();
        this.parcelas = pedidos.getParcelas();
        this.valor = pedidos.getValor();
        this.dataCadastro = pedidos.getDataCadastro();
    }


}
