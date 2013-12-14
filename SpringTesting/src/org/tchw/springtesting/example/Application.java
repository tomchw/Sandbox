package org.tchw.springtesting.example;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.tchw.springtesting.services.CarEngine;
import org.tchw.springtesting.services.MessageSender;

public class Application {

    public static void main(String[] args) {
        Logger.getLogger(Application.class).debug("Init");
        ApplicationContext application = new AnnotationConfigApplicationContext(Config.class);
        application.getBean(MessageSender.class).send("APPLICATION started");
        application.getBean(CarEngine.class).start();
        application.getBean(CarEngine.class).stop();
        Logger.getLogger(Application.class).debug("End");
    }

}