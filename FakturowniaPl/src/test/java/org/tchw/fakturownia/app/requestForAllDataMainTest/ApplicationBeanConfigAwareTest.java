package org.tchw.fakturownia.app.requestForAllDataMainTest;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.tchw.fakturownia.app.config.ApplicationBeanConfig;
import org.tchw.fakturownia.app.config.MayOverrideApplicationBeanConfig;

@ContextHierarchy({
    @ContextConfiguration(name = "override", classes = {MayOverrideApplicationBeanConfig.class}),
    @ContextConfiguration(name = "base", classes = {ApplicationBeanConfig.class})
})
public abstract class ApplicationBeanConfigAwareTest {

}
