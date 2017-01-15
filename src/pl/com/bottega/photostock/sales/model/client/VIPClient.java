package pl.com.bottega.photostock.sales.model.client;

import pl.com.bottega.photostock.sales.model.money.Money;

import java.util.Collection;

public class VIPClient extends Client {

	private Money debitLimit;

	public VIPClient(String name, Address address, Money creditsBalance, Money debitLimit) {
		super(name, address, ClientStatus.VIP, creditsBalance);
		this.debitLimit = debitLimit;
	}

	public VIPClient(String number, String name, Address address, Money creditsBalance, Money debitLimit,
					 boolean active, Collection<Transaction> transactions) {
		super(number, name, address, ClientStatus.VIP, creditsBalance, active, transactions);
		this.debitLimit = debitLimit;
	}

	@Override
	public boolean canAfford(Money amount) {
		return creditsBalance.add(debitLimit).compareTo(amount) >= 0;
	}

	private boolean canAffordWithDebit(Money amount) {
		return creditsBalance.add(debitLimit).compareTo(amount) >= 0;
	}

	public Money getDebitLimit() {
		return debitLimit;
	}
}
