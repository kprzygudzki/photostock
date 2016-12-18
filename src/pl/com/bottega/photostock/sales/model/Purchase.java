package pl.com.bottega.photostock.sales.model;

import java.util.*;

public class Purchase {

	private Client client;
	private Date purchaseDate;
	private List<Product> items;

	public Purchase(Client client, Collection<Product> items) {
		this.client = client;
		this.items = new LinkedList<>(items);
		sortProductsByNumber();
	}

	private void sortProductsByNumber() {
		this.items.sort(new Comparator<Product>() {
			@Override
			public int compare(Product p1, Product p2) {
				return p1.getNumber().compareTo(p2.getNumber());
			}
		});
	}

	public Purchase(Client client, Product... items) {
		this(client, Arrays.asList(items));
	}

	public int getItemsCount() {
		return items.size();
	}
}
