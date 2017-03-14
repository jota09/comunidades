/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitarias;

import fachada.EstructuraFachada;
import fachada.GestionFachada;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class ServicioDeEnvioMail {

    private final Properties properties = new Properties();
    private String password;
    private String mail;
    private Session session;

    public ServicioDeEnvioMail(String hostServer, int portServer, String mail, String user, String password, String starttlls, String auth, String ssl) {
        this.mail = mail;
        this.password = password;
        properties.put("mail.smtp.host", hostServer);
        properties.put("mail.smtp.starttls.enable", starttlls);
        properties.put("mail.smtp.port", portServer);
        properties.put("mail.smtp.mail.sender", mail);
        properties.put("mail.smtp.user", user);
        properties.put("mail.smtp.auth", auth);
        properties.put("mail.smtp.ssl.enable", ssl);

        session = Session.getDefaultInstance(properties);
        //session.setDebug(true);
    }

    public void sendEmail(String mensaje, String sujeto, String destinatario, String[] rutasImagenes) {
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress((String) properties.get("mail.smtp.mail.sender")));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
            message.setSubject(sujeto);
            MimeMultipart multipart = new MimeMultipart("related");
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(mensaje, "text/html");
            multipart.addBodyPart(messageBodyPart);
            int i = 1;
            for (String img : rutasImagenes) {
                messageBodyPart = new MimeBodyPart();
                DataSource fds = new FileDataSource(img);
                messageBodyPart.setDataHandler(new DataHandler(fds));
                messageBodyPart.setHeader("Content-ID", "<image" + i + ">");
                multipart.addBodyPart(messageBodyPart);
                message.setContent(multipart);
                i++;
            }

            Transport t = session.getTransport("smtp");
            t.connect((String) properties.get("mail.smtp.user"), password);
            t.sendMessage(message, message.getAllRecipients());
            t.close();
        } catch (Exception me) {
            me.printStackTrace();
        }

    }
}
