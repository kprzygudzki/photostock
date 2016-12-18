package pl.com.bottega.photostock.sales.model;

public class RationalMoney implements Money {

	public Money opposite() {
		return new RationalMoney(value.negative(), currency);
	}

	public Money add(Money addend) {
		RationalMoney rationalAddend = addend.convertToRational();
		if (currency != rationalAddend.currency)
			throw new IllegalArgumentException("The currencies do not match.");
		return new RationalMoney(value.add(rationalAddend.value), currency);
	}

	public Money subtract(Money subtrahend) {
		RationalMoney rationalSubtrahend = subtrahend.convertToRational();
		if (currency != rationalSubtrahend.currency)
			throw new IllegalArgumentException("The currencies do not match.");
		return new RationalMoney(value.subtract(rationalSubtrahend.value), currency);
	}

	public Money multiply(long factor) {
		return Money.valueOf(value.multiply(factor), currency);
	}

	@Override
	public RationalMoney convertToRational() {
		return this;
	}

	private final Rational value;
	private final Currency currency;

	RationalMoney(Rational value, Currency currency) {
		this.value = value;
		this.currency = currency;
	}

	@Override
	public String toString() {
		return value.toDouble() + " " + currency.name();
	}

	public boolean gte(RationalMoney money) {
		return this.compareTo(money) >= 0;
	}

	public boolean gt(RationalMoney money) {
		return this.compareTo(money) > 0;
	}

	public boolean lte(RationalMoney money) {
		return this.compareTo(money) <= 0;
	}

	public boolean lt(RationalMoney money) {
		return this.compareTo(money) < 0;
	}

	public int compareTo(Money other) {
		RationalMoney rationalMoney = other.convertToRational();
		if (currency != rationalMoney.currency)
			throw new IllegalArgumentException("The currencies do not match.");
		return value.compareTo(rationalMoney.value);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof RationalMoney)) return false;

		RationalMoney money = (RationalMoney) o;

		if (!value.equals(money.value)) return false;
		return currency == money.currency;
	}

	@Override
	public int hashCode() {
		int result = value.hashCode();
		result = 31 * result + currency.hashCode();
		return result;
	}
}