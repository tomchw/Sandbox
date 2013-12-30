package org.tchw.fakturownia.app;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.tchw.fakturownia.app.config.ApplicationBeanConfig;
import org.tchw.fakturownia.app.config.MayOverrideApplicationBeanConfig;

public class ApplicationMain {

    private static GenericApplicationContext applicationContext;

    public static ApplicationContext applicationContext() {
        if( applicationContext == null ) {
            applicationContext = new AnnotationConfigApplicationContext(ApplicationBeanConfig.class, MayOverrideApplicationBeanConfig.class);
            applicationContext.registerShutdownHook();
        }
        return applicationContext;
    }
}
