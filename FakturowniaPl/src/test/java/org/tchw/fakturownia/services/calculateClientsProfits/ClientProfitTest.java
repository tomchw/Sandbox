package org.tchw.fakturownia.services.calculateClientsProfits;

import java.math.BigDecimal;

import org.junit.Test;
import org.tchw.fakturownia.model.Repository;
import org.tchw.fakturownia.services.calculateClientsProfits.ClientProfit;
import org.tchw.fakturownia.services.calculateClientsProfits.ClientProfitCalculator;

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
