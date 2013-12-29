package org.tchw.fakturownia.app.config;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterators;

public class LogBeanPostProcessor implements BeanPostProcessor {

    private final Logger log = Logger.getLogger(getClass());

    @Override
    public Object postProcessAfterInitialization(Object object, String arg1) throws BeansException {
        return object;
    }

    @Override
    public Object postProcessBeforeInitialization(Object object, String name) throws BeansException {
        log.debug("init: " + Iterators.getLast( Splitter.on(".").split(object.toString()).iterator() )+ " - " + name);
        return object;
    }


}
