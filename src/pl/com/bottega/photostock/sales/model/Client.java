package pl.com.bottega.photostock.sales.model;

import java.util.Collection;
import java.util.LinkedList;

public class Client {
	private String name;
	private Address address;
	private ClientStatus status;
	protected Money creditsBalance;
	private Collection<Transaction> transactionHistory;

	public Client(String name, Address address, ClientStatus status, Money initialBalance) {
		this.name = name;
		this.address = address;
		this.status = status;
		this.creditsBalance = initialBalance;
		this.transactionHistory = new LinkedList<>();

		if (!initialBalance.equals(Money.ZERO))
			this.transactionHistory.add(new Transaction(initialBalance, "Openning account"));
	}

	public Client(String name, Address address, Money creditsBalance) {
		this(name, address, ClientStatus.STANDARD, creditsBalance);
	}

	public boolean canAfford(Money amount) {
		return creditsBalance.compareTo(amount) >= 0;
	}

	public void charge(Money amount, String reason) {
		if (amount.lt(Money.ZERO))
			throw new IllegalArgumentException("Value of transaction cannot be negative");

		if (canAfford(amount)) {
			transactionHistory.add(new Transaction(amount.opposite(), reason));
			creditsBalance = creditsBalance.subtract(amount);
		} else {
			throw new CantAffordException(String.format("Requested purchase amount %s exceeds client balance %s", amount, creditsBalance));
		}
	}

	public void recharge(Money amount) {
		if (amount.lte(Money.ZERO))
			throw new IllegalArgumentException("Value of transaction cannot be negative");

		transactionHistory.add(new Transaction(amount, "Recharge account"));
		creditsBalance = creditsBalance.add(amount);
	}

	public String getName() {
		return name;
	}

	public String introduce() {
		String statusName = status.getStatusName();
		return String.format("%s - %s", name, statusName);
	}
}
