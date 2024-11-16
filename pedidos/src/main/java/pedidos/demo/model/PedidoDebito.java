package pedidos.demo.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@DiscriminatorValue("DEBITO")
public class PedidoDebito extends Pedido{

    @NotBlank
    @Size(min = 16, max = 16)
    private String numeroCartao;
    @NotBlank(message = "É obrigatório o código do cartão")
    @Size(min = 3, max = 3)
    private String codigoCartao;

    public PedidoDebito() {
        super();
    }

    public PedidoDebito(Long id, @NotBlank(message = "É obrigatório ter um nome!") String nome, @NotBlank(message = "O CPF deve conter 11 dígitos!") @Size(min = 11, max = 11) String cpf, @NotBlank(message = "A forma de pagamento é obrigatória") FormaDePagamento formaDePagamento, @Email String email, @NotBlank(message = "O telefone é obrigatório") String telefone, @NotBlank(message = "Obrigatório colocar a descrição do pedido!") String descricaoPedido, @NotNull @Positive BigDecimal valor, String numeroCartao, String codigoCartao) {
        super(id, nome, cpf, formaDePagamento, email, telefone, descricaoPedido, valor);
        this.numeroCartao = numeroCartao;
        this.codigoCartao = codigoCartao;
    }

    public PedidoDebito(String numeroCartao, String codigoCartao) {
        this.numeroCartao = numeroCartao;
        this.codigoCartao = codigoCartao;
    }
}
