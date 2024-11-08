package pedidos.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "forma_pagamento", discriminatorType = DiscriminatorType.STRING)
public abstract class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "É obrigatório ter um nome!")
    private String nome;

    @NotBlank(message = "O CPF deve conter 11 dígitos!")
    @Size(min = 11, max = 11)
    private String cpf;

    @Enumerated(EnumType.STRING)
    private FormaDePagamento formaDePagamento;

    private String uuid;

    private String email;

    @NotBlank(message = "O telefone é obrigatório")
    private String telefone;

    @NotBlank(message = "Obrigatório colocar a descrição do pedido!")
    private String descricaoPedido;

    @NotNull
    @Positive
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    private StatusPagamento statusPagamento;

    @CreationTimestamp
    private LocalDateTime dataCadastro;

    private LocalDateTime dataProcessamento = null;


    public Pedido(Long id, String nome, String cpf, FormaDePagamento formaDePagamento, String email, String telefone, String descricaoPedido, BigDecimal valor) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.formaDePagamento = formaDePagamento;
        this.email = email;
        this.telefone = telefone;
        this.descricaoPedido = descricaoPedido;
        this.valor = valor;
        this.statusPagamento = StatusPagamento.AGUARDANDO;
        this.dataCadastro = dataCadastro;
    }
}
