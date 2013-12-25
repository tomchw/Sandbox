package org.tchw.fakturownia.api.cases.client;

import org.tchw.fakturownia.api.model.Client;
import org.tchw.fakturownia.api.model.Repository;

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
