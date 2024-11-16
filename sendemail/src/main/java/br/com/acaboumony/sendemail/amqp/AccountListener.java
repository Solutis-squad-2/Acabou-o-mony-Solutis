package br.com.acaboumony.sendemail.amqp;

import br.com.acaboumony.sendemail.dto.ConfirmacaoCodigoDTO;
import br.com.acaboumony.sendemail.dto.EmailDTO;
import br.com.acaboumony.sendemail.service.MailService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountListener {

    @Autowired
    private MailService mailService;

    @RabbitListener(queues = "Queue.send.emails")
    public void envioEmailCodigo(ConfirmacaoCodigoDTO codigoDTO){
        mailService.sendEmail(codigoDTO.email(), "Codigo de confirmação", codigoDTO.codigo());
    }
    @RabbitListener(queues = "pedido.email-notificacao")
    public void envioEmailStatusPedido(EmailDTO emailDTO){
        mailService.sendEmailPedido(emailDTO);
    }
}
