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
@DiscriminatorValue("PIX")
public class PedidoPix extends Pedido{

    public static final String CHAVE_PIX = "1a1cd635-e894-477c-9ae0-c50b5c1bee53";
    private String chavePix;

    public PedidoPix() {
        super();
        this.chavePix = CHAVE_PIX;
    }


    public PedidoPix(Long id, @NotBlank(message = "É obrigatório ter um nome!") String nome,
                     @NotBlank(message = "O CPF deve conter 11 dígitos!") @Size(min = 11, max = 11)
                     String cpf,
                     @NotBlank(message = "A forma de pagamento é obrigatória") FormaDePagamento formaDePagamento,
                     @Email String email,
                     @NotBlank(message = "O telefone é obrigatório")String telefone,
                     @NotBlank(message = "Obrigatório colocar a descrição do pedido!") String descricaoPedido,
                     @NotNull @Positive BigDecimal valor,
                     String chavePix) {
        super(id, nome, cpf, formaDePagamento, email, telefone, descricaoPedido, valor);
        this.chavePix = CHAVE_PIX;
    }

}
