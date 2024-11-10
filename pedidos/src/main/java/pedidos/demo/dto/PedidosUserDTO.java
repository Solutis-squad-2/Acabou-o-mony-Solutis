package pedidos.demo.dto;

import lombok.Getter;
import lombok.Setter;
import pedidos.demo.model.FormaDePagamento;
import pedidos.demo.model.Pedido;
import pedidos.demo.model.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class PedidosUserDTO {

    private Long id;
    private String nome;
    private String cpf;
    private String uuid;
    private String email;
    private String telefone;
    private FormaDePagamento formaDePagamento;
    private String numeroCartao;
    private String codigoCartao;
    private int parcelas;
    private String descricaoPedido;
    private BigDecimal valor;
    private String chavePix;
    private LocalDateTime dataCadastro;
    private PaymentStatus paymentStatus = PaymentStatus.AGUARDANDO;
    private LocalDateTime dataProcessamento;

    public PedidosUserDTO(Long id, String nome, String cpf, String uuid, String email, String telefone, FormaDePagamento formaDePagamento, String numeroCartao, String codigoCartao, int parcelas, String descricaoPedido, BigDecimal valor, PaymentStatus paymentStatus, LocalDateTime dataCadastro) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.uuid = uuid;
        this.email = email;
        this.telefone = telefone;
        this.formaDePagamento = formaDePagamento;
        this.numeroCartao = numeroCartao;
        this.codigoCartao = codigoCartao;
        this.parcelas = parcelas;
        this.descricaoPedido = descricaoPedido;
        this.valor = valor;
        //this.chavePix = chavePix;
        this.dataProcessamento = dataCadastro;
        this.paymentStatus = paymentStatus;
    }

    public PedidosUserDTO() {
    }

    public PedidosUserDTO(Pedido pedido) {
        if (pedido != null) {
            this.id = pedido.getId();
            this.nome = pedido.getNome();
            this.cpf = pedido.getCpf();
            this.uuid = pedido.getUuid();
            this.email = pedido.getEmail();
            this.formaDePagamento = pedido.getFormaDePagamento();
            this.numeroCartao = numeroCartao;
            this.codigoCartao = codigoCartao;
            this.parcelas = parcelas;
            this.telefone = pedido.getTelefone();
            this.descricaoPedido = pedido.getDescricaoPedido();
            this.valor = pedido.getValor();
            this.chavePix = getChavePix();
            this.paymentStatus = pedido.getPaymentStatus();
            this.dataCadastro = pedido.getDataCadastro();


        }
    }

    public FormaDePagamento getFormaDePagamento() {
        return formaDePagamento;
    }

    public void setFormaDePagamento(FormaDePagamento formaDePagamento) {
        this.formaDePagamento = formaDePagamento;
    }
}
