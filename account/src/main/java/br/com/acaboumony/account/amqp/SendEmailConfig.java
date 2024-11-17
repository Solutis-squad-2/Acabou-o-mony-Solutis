package br.com.acaboumony.account.amqp;


import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SendEmailConfig {

    @Bean
    public Queue sendEmail(){
        return new Queue("Queue.send.emails",false);
    }
    //criação da fila
    @Bean
    public Queue criaFilaPayment(){
        return QueueBuilder.nonDurable("pedido.cadastro")
                .deadLetterExchange("")
                .deadLetterRoutingKey("pedido.cadastro.dlq")
                .build();
    }



    //FILA DLQ PAGAMENTO
    @Bean
    public Queue criarFilaPaymentDLQ(){
        return QueueBuilder.nonDurable("pedido.cadastro.dlq").build();
    }


    @Bean
    public Queue criaFilaEmail(){
        return QueueBuilder.nonDurable("pedido.email-notificacao")
                .deadLetterExchange("")
                .deadLetterRoutingKey("pedido.email-notificacao.dlq")
                .build();
    }

    //FILA DLQ PARA EMAIL
    @Bean
    public Queue criarFilaEmailDLQ(){
        return QueueBuilder.nonDurable("pedido.email-notificacao.dlq").build();
    }
    public static final String QUEUE_NAME = "fila.confirmacao.payment";

    @Bean
    public Queue paymentQueue() {
        return new Queue(QUEUE_NAME, false);
    }

    @Bean
    public RabbitAdmin createAdmin(ConnectionFactory conn){
        return new RabbitAdmin(conn);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> initAdmin(RabbitAdmin rabbitAdmin){
        return  event -> rabbitAdmin.initialize();
    }
    @Bean
    public Jackson2JsonMessageConverter messageConverter(){
        return  new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate (ConnectionFactory connectionFactory,
                                          Jackson2JsonMessageConverter messageConverter){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }
}
