package br.com.acaboumony.account.amqp;

im
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SendEmailConfig {

    @Bean
    public Queue sendEmail(){
        return new Queue("Queue.send.emails",false);
    }

    public RabbitAdmin createAdmin(){

    }
}
