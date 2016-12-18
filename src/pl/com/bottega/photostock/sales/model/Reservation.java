package pl.com.bottega.photostock.sales.model;

import java.util.Collection;
import java.util.LinkedList;

public class Reservation {

	private Client client;
	private Collection<Product> items;

	public Reservation(Client client) {
		this.client = client;
		this.items = new LinkedList<>();
	}

	public void add(Product product) {
		if (items.contains(product))
			throw new IllegalArgumentException(String.format("Product number %s is already in this reservation", product.getNumber()));
		product.ensureAvailability();

		items.add(product);
	}

	public void remove(Product product) {
		if (!items.contains((product)))
			throw new IllegalArgumentException(String.format("Product number %s was not found in this reservation", product.getNumber()));

		items.remove(product);
	}

	public Offer generateOffer() {
		return new Offer(getActiveItems(), client);
	}

	private Collection<Product> getActiveItems() {
		Collection<Product> products = new LinkedList<>();

		for (Product product : items)
			if (!product.isActive())
				products.add(product);

		return products;
	}

	public int getItemsCount() {
		return items.size();
	}
}
