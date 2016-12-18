package pl.com.bottega.photostock.sales.model;

public abstract class AbstractProduct implements Product {
	protected String number;
	private String name;
	protected Money catalogPrice;
	private boolean active;
	private Client reservationOwner;
	private Client buyer;

	public AbstractProduct(Money catalogPrice, String number, String name, boolean active) {
		this.catalogPrice = catalogPrice;
		this.active = active;
		this.number = number;
		this.name = name;
	}

	@Override
	public abstract Money calculatePrice(Client client);

	@Override
	public boolean isAvailable() {
		return active && !isSold() && !isReserved();
	}

	private boolean isReserved() {
		return reservationOwner != null;
	}

	private boolean isSold() {
		return buyer != null;
	}

	@Override
	public void reservedPer(Client client) {
		ensureAvailability();
		reservationOwner = client;
	}

	@Override
	public void unreservedPer(Client client) {
		ensureReservedByClient(client);
		reservationOwner = null;
	}

	private void ensureReservedByClient(Client client) {
		if (!isReservedByClient(client)) {
			throw new IllegalArgumentException(String.format("Product %s is not reserved by %s", getNumber(), client.getName()));
		}
	}

	private boolean isReservedByClient(Client client) {
		return isReserved() || client.equals(reservationOwner);
	}

	@Override
	public void soldPer(Client client) {
		ensureReservedByClient(client);
		buyer = client;
		unreservedPer(client);
	}

	@Override
	public String getNumber() {
		return number;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean isActive() {
		return active;
	}

	@Override
	public void deactivate() {
		active = false;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		AbstractProduct product = (AbstractProduct) o;

		return number != null ? number.equals(product.number) : product.number == null;
	}

	@Override
	public int hashCode() {
		return number != null ? number.hashCode() : 0;
	}
}
