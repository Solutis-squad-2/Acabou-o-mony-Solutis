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
@DiscriminatorValue("CREDITO")
public class PedidoCredito extends Pedido{

    @NotBlank(message = "O número do cartão é obrigatório")
    @Size(min = 16, max = 16)
    private String numeroCartao;

    @NotBlank(message = "Código do cartão é obrigatório")
    @Size(min = 3, max = 3)
    private String codigoCartao;
    
    @Positive
    private int parcelas;

    public PedidoCredito(){
        super();
    }

    public PedidoCredito(Long id, @NotBlank(message = "É obrigatório ter um nome!") String nome, @NotBlank(message = "O CPF deve conter 11 dígitos!") @Size(min = 11, max = 11) String cpf, @NotBlank(message = "A forma de pagamento é obrigatória") FormaDePagamento formaDePagamento, @Email String email, @NotBlank(message = "O telefone é obrigatório") String telefone, @NotBlank(message = "Obrigatório colocar a descrição do pedido!") String descricaoPedido, @NotNull @Positive BigDecimal valor, String numeroCartao, String codigoCartao, int parcelas) {
        super(id, nome, cpf, formaDePagamento, email, telefone, descricaoPedido, valor);
        this.numeroCartao = numeroCartao;
        this.codigoCartao = codigoCartao;
        this.parcelas = parcelas;
    }

    public PedidoCredito(String numeroCartao, String codigoCartao, int parcelas) {
        this.numeroCartao = numeroCartao;
        this.codigoCartao = codigoCartao;
        this.parcelas = parcelas;
    }
}
