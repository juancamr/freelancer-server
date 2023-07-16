package com.jmatch.config;

import io.github.cdimascio.dotenv.Dotenv;

import java.util.Properties;
import javax.mail.*;

public class EmailSender {
    public static Session session;
    private static final Dotenv dotenv = Dotenv.load();

    public static void init() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", dotenv.get("SMTP_HOST"));
        properties.put("mail.smtp.port", dotenv.get("SMTP_PORT"));
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        final String username = dotenv.get("MY_PERSONAL_EMAIL");
        final String password = dotenv.get("MY_EMAIL_PASSWORD");

        session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    public static Session getSession() {
        if (session == null)
            init();
        return session;
    }
}
