package pl.com.bottega.photostock.sales.infrastructure;

import pl.com.bottega.photostock.sales.model.Address;
import pl.com.bottega.photostock.sales.model.Client;
import pl.com.bottega.photostock.sales.model.ClientRepository;
import pl.com.bottega.photostock.sales.model.VIPClient;
import pl.com.bottega.photostock.sales.model.money.Money;
import pl.com.bottega.photostock.sales.model.money.RationalMoney;

import java.util.HashMap;
import java.util.Map;

public class InMemoryClientRepository implements ClientRepository {

	private static final Map<String, Client> REPOSITORY = new HashMap<>();

	static {
		Client client = new Client("Johnny X", new Address(), Money.valueOf(100));
		Client vipClient = new VIPClient("Mr. Marcellus Wallace", new Address(), RationalMoney.ZERO, Money.valueOf(100));
		REPOSITORY.put(client.getNumber(), client);
		REPOSITORY.put(vipClient.getNumber(), vipClient);
	}

	@Override
	public Client get(String clientNumber) {
		return REPOSITORY.get(clientNumber);
	}
}
