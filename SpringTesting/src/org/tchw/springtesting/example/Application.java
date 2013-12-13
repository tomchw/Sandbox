package org.tchw.springtesting.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.tchw.springtesting.services.MessageSender;

public class Application {

    public static void main(String[] args) {
          ApplicationContext application = new AnnotationConfigApplicationContext(Config.class);
          MessageSender messageSender = application.getBean(MessageSender.class);
          messageSender.send("ABC");
    }
    
}