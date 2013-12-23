package org.tchw.fakturownia.api.cases.client;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;
import org.tchw.fakturownia.api.model.Repository;

public class ClientProfitTest {

    @Test
    public void test() {
        Repository repository = Repository.forTest(getClass());
        ClientProfitCalculator clientProfitCalculator = new ClientProfitCalculator(repository);

        ClientProfit clientProfit = clientProfitCalculator.calculate(repository.clients.all().iterator().next());
        Assert.assertTrue(new BigDecimal(15000).setScale(2).equals(clientProfit.profitValue()));
    }

}
