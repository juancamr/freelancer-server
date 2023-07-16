package com.jmatch.utils;

import com.jmatch.config.EmailSender;
import org.mindrot.jbcrypt.BCrypt;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Utils {
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    public static String encryptPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean comparePassword(String enteredPassword, String hashedPassword) {
        return BCrypt.checkpw(enteredPassword, hashedPassword);
    }

    public static boolean isValidEmail(String email) {
        return Pattern.compile(EMAIL_REGEX).matcher(email).matches();
    }

    public static <T> boolean checkRequestParams(T objeto) {
        Field[] fields = objeto.getClass().getDeclaredFields();
        List<Object> params = new ArrayList<>();
        for (Field field : fields) {
            field.setAccessible(true); //importantisimo xd
            try {
                params.add(field.get(objeto));
            } catch (IllegalAccessException e) {
                params.add(null);
                System.out.println(e);
            }
        }
        for (Object param : params)
            if (param == null || param.toString().trim().isEmpty())
                return false;

        return true;
    }

    boolean sendMail(String email, String titulo, String textMessage) {
        try {
            Session session = EmailSender.getSession();
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("jcmrojas29@gmail.comxd"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject(titulo);
            message.setText(textMessage);
            Transport.send(message);
            System.out.println("El correo se ha enviado correctamente.");
            return true;
        } catch (MessagingException e) {
            System.out.println("Error al enviar el correo: " + e.getMessage());
            return false;
        }
    }

}
