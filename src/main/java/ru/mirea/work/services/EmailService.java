package ru.mirea.work.services;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

/**
 * Класс-сервис для отправки сообщений на электронную почту

 */
@Service
public class EmailService {

    /**
     * Асинхронный метод отправки сообщения на почту
     * @param mail  Адрес электронной почты
     * @param message Сообщение
     * @param isManager Переменная показывающая посылается ли письмо менеджеру или обычному пользователю
     * @throws AddressException Исключение, возникающее при обнаружении неправильно отформатированного адреса
     * @throws MessagingException Базовый класс для всех исключений, создаваемых классами обмена сообщениями
     * @throws IOException Сигнализирует о том, что произошло какое-либо исключение ввода-вывода. Этот класс является общим классом исключений, создаваемых неудачными или прерванными операциями ввода-вывода.
     */
    @Async
    public void sendmail(String mail, String message, boolean isManager) throws AddressException, MessagingException, IOException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("semenov.ds12@gmail.com", "wbfatlobyccnsdqv");
            }
        });

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("semenov.ds12@gmail.com", false));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail));
        if (isManager)
            msg.setSubject("GUSTUM/был оформлен новый заказ");
        else
            msg.setSubject("GUSTUM/был оформлен новый заказ");
        msg.setSentDate(new Date());

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(message, "text/html; charset=UTF-8");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        msg.setContent(multipart);
        Transport.send(msg);
    }
}
