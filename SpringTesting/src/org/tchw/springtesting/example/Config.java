package org.tchw.springtesting.example;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tchw.springtesting.services.MessageSender;
import org.tchw.springtesting.services.MyBeanPostProcessor;
import org.tchw.springtesting.services.impl.MessageSenderImpl;

@Configuration
public class Config {

    @Bean
    public MessageSender messageSender() {
        return new MessageSenderImpl();
    }

    @Bean
    public BeanPostProcessor beanPostProcessor() {
        return new MyBeanPostProcessor();
    }

}
