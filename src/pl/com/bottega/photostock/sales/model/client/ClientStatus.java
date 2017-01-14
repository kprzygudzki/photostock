package pl.com.bottega.photostock.sales.model.client;

public enum ClientStatus {

	STANDARD("Standard"),
	VIP("VIP"),
	GOLD("Złoty"),
	SILVER("Srebrny"),
	PLATINUM("Platynowy");

	ClientStatus(String statusName) {
		this.statusName = statusName;
	}

	private String statusName;

	public String getStatusName() {
		return statusName;
	}
}
