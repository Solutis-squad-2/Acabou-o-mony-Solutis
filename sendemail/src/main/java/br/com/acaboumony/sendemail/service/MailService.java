package br.com.acaboumony.sendemail.service;

import br.com.acaboumony.sendemail.exception.SendEmailException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

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
}
