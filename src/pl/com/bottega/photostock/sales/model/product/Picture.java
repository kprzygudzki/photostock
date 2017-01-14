package pl.com.bottega.photostock.sales.model.product;

import pl.com.bottega.photostock.sales.model.client.Client;
import pl.com.bottega.photostock.sales.model.money.Money;

import java.util.Collection;
import java.util.HashSet;

public class Picture extends AbstractProduct {

	private Collection<String> tags;

	public Picture(String number, String name, Collection<String> tags, Money catalogPrice, boolean active) {
		super(catalogPrice, number, name, active);
		this.tags = new HashSet<>(tags);
	}

	public Picture(String number, String name, Collection<String> tags, Money catalogPrice) {
		this(number, name, tags, catalogPrice, true);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Picture)) return false;

		Picture picture = (Picture) o;

		return number != null ? number.equals(picture.number) : picture.number == null;
	}

	@Override
	public int hashCode() {
		return number != null ? number.hashCode() : 0;
	}

	@Override
	public Money calculatePrice(Client client) {
		return catalogPrice;
	}

	public boolean hasTag(String tag) {
		return tags.contains(tag);
	}
}