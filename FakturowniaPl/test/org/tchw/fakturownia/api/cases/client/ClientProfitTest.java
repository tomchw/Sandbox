package org.tchw.fakturownia.api.cases.client;

import java.math.BigDecimal;

import org.junit.Test;
import org.tchw.fakturownia.data.cases.profit.ClientProfit;
import org.tchw.fakturownia.data.cases.profit.ClientProfitCalculator;
import org.tchw.fakturownia.data.model.Repository;

import static org.junit.Assert.assertEquals;

public class ClientProfitTest {

    @Test
    public void test() {
        Repository repository = Repository.forTest(getClass());
        ClientProfitCalculator clientProfitCalculator = new ClientProfitCalculator(repository);

        ClientProfit clientProfit = clientProfitCalculator.calculate(repository.clients.all().iterator().next());
        assertEquals(new BigDecimal(6500).setScale(2), clientProfit.profitValue());
    }

}
