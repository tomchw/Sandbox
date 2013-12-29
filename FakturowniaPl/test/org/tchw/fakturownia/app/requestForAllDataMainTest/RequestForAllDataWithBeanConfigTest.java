package org.tchw.fakturownia.app.requestForAllDataMainTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.tchw.fakturownia.model.Repository;
import org.tchw.fakturownia.services.RequestForAllData;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
    @ContextConfiguration(name = "override", classes = {TestApplicationBeanConfig.class})
})
public class RequestForAllDataWithBeanConfigTest extends ApplicationBeanConfigAwareTest {

    @Autowired
    private ApplicationContext app;

    @Test
    public void test() {
        app.getBean(RequestForAllData.class).requestForAllData();
        assertEquals("20002", app.getBean(Repository.class).invoices.byId("30001").positions().get(1).productId());
    }

}
