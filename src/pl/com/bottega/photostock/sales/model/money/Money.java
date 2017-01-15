package pl.com.bottega.photostock.sales.model.money;

public interface Money extends Comparable<Money> {

	enum Currency {CREDIT;}

	Currency DEFAULT_CURRENCY = Currency.CREDIT;

	Money ZERO = Money.valueOf(0, DEFAULT_CURRENCY);

	static Money valueOf(String value) {
		String[] parts = value.split(" ");
		if (parts.length != 1 && parts.length != 2)
			throw new IllegalArgumentException("Invalid money format");
		long amount = (long) (Double.valueOf(parts[0]) * 100);
		if (parts.length == 1)
			return new IntegerMoney(amount, DEFAULT_CURRENCY);
		else {
			Currency currency = Currency.valueOf(parts[1]);
			return new IntegerMoney(amount, currency);
		}
	}

	static Money valueOf(Rational value, Currency currency) {
		return new RationalMoney(value, currency);
	}

	static Money valueOf(long value, Currency currency) {
		return new IntegerMoney(value, currency);
	}

	static Money valueOf(long value) {
		return new IntegerMoney(value, DEFAULT_CURRENCY);
	}

	static Money valueOf(float value) {
		return new IntegerMoney((long) (value * 100L), DEFAULT_CURRENCY);
	}

	Money opposite();

	Money add(Money addend);

	Money subtract(Money subtrahend);

	Money multiply(long factor);

	RationalMoney convertToRational();

	IntegerMoney convertToInteger();

	default boolean gte(Money money) {
		return compareTo(money) >= 0;
	}

	default boolean gt(Money money) {
		return compareTo(money) > 0;
	}

	default boolean lte(Money money) {
		return compareTo(money) <= 0;
	}

	default boolean lt(Money money) {
		return compareTo(money) < 0;
	}

	String toString();
}
