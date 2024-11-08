package pedidos.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pedidos.demo.model.FormaDePagamento;
import pedidos.demo.model.StatusPagamento;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class PaymentDTO {
    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private FormaDePagamento formaDePagamento;
    private String numeroCartao;
    private String codigoCartao;
    private int parcelas;
    private BigDecimal valor;
    private LocalDateTime dataCadastro;
    private LocalDateTime dataProcessamento;
    private StatusPagamento statusPagamento;


    // Construtor para pagamento com crédito
    public PaymentDTO(Long id, String nome, String cpf, String email, FormaDePagamento formaDePagamento,
                      String numeroCartao, String codigoCartao, int parcelas, BigDecimal valor, LocalDateTime dataCadastro) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.formaDePagamento = formaDePagamento;
        this.numeroCartao = numeroCartao;
        this.codigoCartao = codigoCartao;
        this.parcelas = parcelas;
        this.valor = valor;
        this.dataCadastro = dataCadastro;
    }

    // Construtor para pagamento com débito
    public PaymentDTO(Long id, String nome, String cpf, String email, FormaDePagamento formaDePagamento,
                      String numeroCartao, String codigoCartao, BigDecimal valor, LocalDateTime dataCadastro) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.formaDePagamento = formaDePagamento;
        this.numeroCartao = numeroCartao;
        this.codigoCartao = codigoCartao;
        this.valor = valor;
        this.dataCadastro = dataCadastro;
    }

    // Construtor para pagamento com Pix
    public PaymentDTO(Long id, String nome, String cpf, String email, FormaDePagamento formaDePagamento,
                      BigDecimal valor, LocalDateTime dataCadastro) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.formaDePagamento = formaDePagamento;
        this.valor = valor;
        this.dataCadastro = dataCadastro;
    }

    // Construtor para resposta com status e data de processamento (após o processamento do pagamento)
    public PaymentDTO(Long id, LocalDateTime dataProcessamento, String statusPagamento) {
        this.id = id;
        this.dataProcessamento = dataProcessamento;
        this.statusPagamento = StatusPagamento.valueOf(statusPagamento);
    }
}
