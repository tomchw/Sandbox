package org.tchw.fakturownia.services;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.tchw.fakturownia.app.ApplicationBeanConfig;
import org.tchw.fakturownia.app.MayOverrideApplicationBeanConfig;

@ContextHierarchy({
    @ContextConfiguration(name = "override", classes = {MayOverrideApplicationBeanConfig.class}),
    @ContextConfiguration(name = "base", classes = {ApplicationBeanConfig.class})
})
public abstract class ApplicationBeanConfigAwareTest {

}
