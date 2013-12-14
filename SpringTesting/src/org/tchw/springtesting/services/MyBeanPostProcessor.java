package org.tchw.springtesting.services;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class MyBeanPostProcessor implements BeanPostProcessor {

    private final Logger log = Logger.getLogger(getClass());

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        log.debug("Before " + beanName + ": " + bean.getClass().getSimpleName());
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        log.debug("After " + beanName + ": " + bean.getClass().getSimpleName());
        return bean;
    }

}
