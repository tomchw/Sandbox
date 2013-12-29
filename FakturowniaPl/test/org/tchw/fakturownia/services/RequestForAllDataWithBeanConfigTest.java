package org.tchw.fakturownia.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
    @ContextConfiguration(name = "override", classes = {TestApplicationBeanConfig.class})
})
public class RequestForAllDataWithBeanConfigTest extends ApplicationBeanConfigAwareTest {

    @Autowired
    private ApplicationContext application;

    @Test
    public void test() {
        //application.getBean(RequestForAllData.class).requestForAllData();
    }

}
