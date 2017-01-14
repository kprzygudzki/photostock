package pl.com.bottega.photostock.sales.model.client;

import pl.com.bottega.photostock.sales.model.money.Money;

import java.time.LocalDateTime;
import java.util.Date;

public class Transaction {

	private Money hajsik;
	private String description;
	private LocalDateTime timestamp;

	public Transaction(Money hajsik, String description) {
		this.hajsik = hajsik;
		this.description = description;
		this.timestamp = LocalDateTime.now();
	}
}
