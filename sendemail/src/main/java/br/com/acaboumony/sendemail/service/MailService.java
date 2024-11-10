package br.com.acaboumony.sendemail.service;

import br.com.acaboumony.sendemail.dto.EmailDTO;
import br.com.acaboumony.sendemail.dto.FormaDePagamento;
import br.com.acaboumony.sendemail.dto.PaymentStatus;
import br.com.acaboumony.sendemail.exception.SendEmailException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String remetente;


    public void sendEmail(String destinatario, String assunto, String codigo){
        String mensagemHtml = """
    <html>
        <body style="font-family: Arial, sans-serif; text-align: center; color: #333;">
            <h2 style="color: #333; text-align: center;">Olar! Aqui é da <strong>Acabou o Mony</strong></h2>
            <p style="font-size: 16px; text-align: center;">Abaixo está o seu código de confirmação:</p>
            <div style="
                background-color: #ffe6f0;
                color: #b8860b;
                font-size: 24px;
                font-weight: bold;
                padding: 20px;
                border-radius: 8px;
                display: inline-block;
                margin-top: 10px;">
                """ + codigo + """
            </div>
            <p style="margin-top: 20px; font-size: 14px; color: #666; text-align: center;">Use este código para confirmar sua identidade.</p>
            <p style="font-size: 14px; color: #999; text-align: center;">Atenciosamente, <br>Equipe Acabou o Mony</p>
        </body>
    </html>
    """;
        try{
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setFrom(remetente);
            helper.setTo(destinatario);
            helper.setSubject(assunto);
            helper.setText(mensagemHtml, true);

            javaMailSender.send(mimeMessage);
        } catch (Exception e){
            throw new SendEmailException("Erro ao enviar o email");
        }
    }
    public void sendEmailPedido(EmailDTO emailDTO) {
        // Construção das mensagens de valor e parcelas
        String valorMessage;
        String parcelasMessage = "";

        // Verifica a forma de pagamento e ajusta a mensagem
        if (emailDTO.getFormaDePagamento() == FormaDePagamento.CREDITO) {
            // Para crédito, divide o valor pelo número de parcelas
            BigDecimal valorParcelado = emailDTO.getValor().divide(BigDecimal.valueOf(emailDTO.getParcelas()), 2, RoundingMode.HALF_UP);
            parcelasMessage = "<p><strong>Valor por parcela:</strong> " + emailDTO.getParcelas() + "x de R$ " + valorParcelado.toString() + "</p>";
            valorMessage = "<p><strong>Valor total do pedido:</strong> R$ " + emailDTO.getValor().toString() + "</p>";
        } else {
            // Para débito, mostra apenas o valor total
            valorMessage = "<p><strong>Valor total do pedido:</strong> R$ " + emailDTO.getValor().toString() + "</p>";
        }

        // Mensagem condicional baseada no status do pagamento
        String statusMessage;
        if (emailDTO.getPaymentStatus() == PaymentStatus.AGUARDANDO) {
            statusMessage = "<h3 style=\"color: #ff9900;\">Seu pedido foi realizado e está aguardando confirmação de pagamento.</h3>";
        } else if (emailDTO.getPaymentStatus() == PaymentStatus.CONFIRMADO) {
            statusMessage = "<h3 style=\"color: #28a745;\">Parabéns! Seu pagamento foi confirmado com sucesso.</h3>";
        } else {
            statusMessage = "<h3 style=\"color: #333;\">Status do pedido: " + emailDTO.getPaymentStatus().toString() + "</h3>";
        }

        // Montagem do HTML para o corpo do email
        String mensagemHtml = "<html>" +
                "<body style=\"font-family: Arial, sans-serif; text-align: center; color: #333;\">" +
                "<h2 style=\"color: #333; text-align: center;\">Olá, <strong style=\"font-size: 32px;\">" + emailDTO.getNome() + "</strong></h2>" +
                "<p style=\"font-size: 18px; text-align: center;\">Aqui estão os detalhes do seu pedido:</p>" +
                statusMessage +
                "<p style=\"margin-top: 20px; font-size: 16px; text-align: left;\">Descrição do Pedido: " + emailDTO.getDescricaoPedido() + "</p>" +
                valorMessage +
                parcelasMessage +
                "<p style=\"margin-top: 20px; font-size: 14px; color: #666;\">Obrigado por confiar em nós para o seu pedido.</p>" +
                "<p style=\"font-size: 14px; color: #999;\">Atenciosamente, <br>Equipe Acabou o Mony</p>" +
                "</body>" +
                "</html>";

        // Envio do email
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setFrom(remetente);
            helper.setTo(emailDTO.getEmail());
            helper.setSubject("Confirmação de Pedido - Acabou o Mony");
            helper.setText(mensagemHtml, true);

            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new SendEmailException("Erro ao enviar o email: " + e.getMessage());
        }
    }


}
