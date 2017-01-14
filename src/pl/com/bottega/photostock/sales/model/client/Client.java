package pl.com.bottega.photostock.sales.model.client;

import pl.com.bottega.photostock.sales.model.money.Money;

import java.util.Collection;
import java.util.LinkedList;

public class Client {

	private static int serialNumber = 0;

	private String name;
	private Address address;
	private ClientStatus status;
	protected Money creditsBalance;
	private Collection<Transaction> transactionHistory;
	private boolean active;
	private String number;

	public Client(String name, Address address, ClientStatus status, Money initialBalance) {
		this.name = name;
		this.address = address;
		this.status = status;
		this.creditsBalance = initialBalance;
		this.transactionHistory = new LinkedList<>();
		this.number = nextNumber();
		this.active = true;

		if (!initialBalance.equals(Money.ZERO))
			this.transactionHistory.add(new Transaction(initialBalance, "Openning account"));
	}

	private String nextNumber() {
		serialNumber += 100;
		return String.valueOf(serialNumber);
	}

	public Client(String name, Address address, Money creditsBalance) {
		this(name, address, ClientStatus.STANDARD, creditsBalance);
	}

	public boolean canAfford(Money amount) {
		return creditsBalance.compareTo(amount) >= 0;
	}

	public void charge(Money amount, String reason) {
		if (amount.compareTo(Money.ZERO) < 0)
			throw new IllegalArgumentException("Value of transaction cannot be negative");

		if (canAfford(amount)) {
			creditsBalance = creditsBalance.subtract(amount);
			transactionHistory.add(new Transaction(amount.opposite(), reason));
		} else
			throw new CantAffordException(String.format("Requested purchase amount %s exceeds client balance %s", amount, creditsBalance));
	}

	public void recharge(Money amount) {
		if (amount.compareTo(Money.ZERO) <= 0)
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

	public boolean isActive() {
		return active;
	}

	public void deactivate() {
		active = false;
	}

	public String getNumber() {
		return number;
	}
}
