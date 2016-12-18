package pl.com.bottega.photostock.sales.model;

public class Clip extends AbstractProduct {

	public static final Long FIVE_MINUTES = 300_000l;
	private final Long length;

	public Clip(String number, String name, Long length, Money catalogPrice) {
		super(catalogPrice, number, name, true);
		this.length = length;
	}

	@Override
	public Money calculatePrice(Client client) {
		if (length > FIVE_MINUTES)
			return catalogPrice.multiply(2);

		return catalogPrice;
	}
}
