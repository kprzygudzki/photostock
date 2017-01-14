package pl.com.bottega.photostock.sales.model.product;

import pl.com.bottega.photostock.sales.model.client.Client;
import pl.com.bottega.photostock.sales.model.money.Money;

public class Clip extends AbstractProduct {

	private static final Long FIVE_MINUTES = 300_000L;
	private final Long length;

	public Clip(String number, String name, Long length, Money catalogPrice) {
		super(catalogPrice, number, name, true);
		this.length = length;
	}

	@Override
	public Money calculatePrice(Client client) {
		if (length > FIVE_MINUTES)
			return catalogPrice.multiply(2);
		else
			return catalogPrice;
	}
}
