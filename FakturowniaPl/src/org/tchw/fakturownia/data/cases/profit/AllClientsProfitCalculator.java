package org.tchw.fakturownia.data.cases.profit;

import org.tchw.fakturownia.data.model.Client;
import org.tchw.fakturownia.data.model.Repository;

import com.google.common.collect.ImmutableList;

public class AllClientsProfitCalculator {

    private final Repository repository;
    private final ClientProfitCalculator clientProfilCalculator;

    public AllClientsProfitCalculator(Repository repository, ClientProfitCalculator clientProfilCalculator) {
        this.repository = repository;
        this.clientProfilCalculator = clientProfilCalculator;
    }

    public void calculate(Handling handling) {
        ImmutableList<Client> clients = repository.clients.all();
        for (Client client : clients) {
            try {
                handling.onClientProfit( clientProfilCalculator.calculate(client) );
            } catch(RuntimeException e) {
                e.printStackTrace();
            }
        }
    }

    public interface Handling {

        public void onClientProfit(ClientProfit clientProfit);

    }
}
