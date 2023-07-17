package com.jmatch;

import com.jmatch.config.EmailSender;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JmatchServerApplication {

    public static void main(String[] args) {
        //EmailSender.init();
        SpringApplication.run(JmatchServerApplication.class, args);
    }

}
