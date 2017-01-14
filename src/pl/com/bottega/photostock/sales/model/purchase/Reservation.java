package pl.com.bottega.photostock.sales.model.purchase;

import pl.com.bottega.photostock.sales.model.client.Client;
import pl.com.bottega.photostock.sales.model.product.Product;

import java.util.Collection;
import java.util.LinkedList;
import java.util.UUID;

public class Reservation {

	private Client owner;
	private Collection<Product> items;
	private String number;
	private boolean active;

	public Reservation(Client client) {
		this.owner = client;
		this.items = new LinkedList<>();
		this.number = UUID.randomUUID().toString();
		this.active = true;
	}

	public void add(Product product) {
		if (items.contains(product))
			throw new IllegalArgumentException(String.format("Product number %s is already in this reservation", product.getNumber()));
		product.ensureAvailability();
		items.add(product);
		product.reservedPer(owner);
	}

	public void remove(Product product) {
		if (!items.contains((product)))
			throw new IllegalArgumentException(String.format("Product number %s was not found in this reservation", product.getNumber()));
		items.remove(product);
	}

	public Offer generateOffer() {
		Collection<Product> products = getActiveItems();
		if (products.isEmpty())
			throw new IllegalStateException("There are no active items in the reservation");
		return new Offer(getActiveItems(), owner);
	}

	private Collection<Product> getActiveItems() {
		Collection<Product> products = new LinkedList<>();
		for (Product product : items)
			if (product.isActive())
				products.add(product);
		return products;
	}

	public int getItemsCount() {
		return items.size();
	}

	public String getNumber() {
		return number;
	}

	public Client getOwner() {
		return owner;
	}

	public boolean isOwnedBy(String clientNumber) {
		return getOwner().getNumber().equals(clientNumber);
	}

	public void deactivate() {
		active = false;
	}

	public boolean isActive() {
		return active;
	}
}
