package pl.com.bottega.photostock.sales.model.product;

import pl.com.bottega.photostock.sales.model.client.Client;
import pl.com.bottega.photostock.sales.model.money.Money;

public interface Product {

	boolean isAvailable();

	Money calculatePrice(Client client);

	void reservedPer(Client client);

	void unreservedPer(Client client);

	void soldPer(Client client);

	String getNumber();

	String getName();

	boolean isActive();

	void deactivate();

	default void ensureAvailability() {
		if (!isAvailable())
			throw new ProductNotAvailableException(this);
	}
}
