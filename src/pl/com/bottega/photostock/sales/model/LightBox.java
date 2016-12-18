package pl.com.bottega.photostock.sales.model;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

public class LightBox implements Iterable<Product> {
	private Collection<Product> items = new LinkedList<>();
	private Client owner;
	private String name;

	public LightBox(Client client, String name) {
		this.owner = client;
		this.name = name;
	}

	public void add(Product product) {
		if (items.contains(product))
			throw new IllegalArgumentException(String.format("LightBox %s already contains product %s", name, product.getNumber()));
		product.ensureAvailability();

		items.add(product);
	}

	public void remove(Product product) {
		if (!items.contains(product))
			throw new IllegalArgumentException(String.format("LightBox %s doesn't contain product %s", name, product.getNumber()));

		items.remove(product);
	}

	public void rename(String newName) {
		this.name = newName;
	}

	public String getName() {
		return name;
	}

	public Client getOwner() {
		return owner;
	}

	@Override
	public Iterator<Product> iterator() {
		return items.iterator();
	}

	public static LightBox joined(Client client, String name, LightBox ... lightBoxes) {
		LightBox newLightBox = new LightBox(client, name);

		for (LightBox lb : lightBoxes) {
			for (Product product : lb.items) {
				if (!newLightBox.contains(product) && product.isActive())
					newLightBox.items.add(product);
			}
		}
		return newLightBox;
	}

	private boolean contains(Product product) {
		return items.contains(product);
	}
}
