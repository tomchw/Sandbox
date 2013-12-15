package org.tchw.springtesting.services;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;

public class MyBeanPostProcessor implements BeanPostProcessor {

    private final Logger log = Logger.getLogger(getClass());

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        log.debug("Started creating " + beanName + ": " + onlySimpleClassNameAndNumber(bean));
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        log.debug("Finished creating " + beanName + ": " + onlySimpleClassNameAndNumber(bean));
        return bean;
    }

    private static String onlySimpleClassNameAndNumber(Object object) {
        Iterable<String> split = Splitter.on(".").trimResults().split(object.toString());
        return Iterables.getLast(split);
    }
}
