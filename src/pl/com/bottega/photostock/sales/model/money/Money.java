package pl.com.bottega.photostock.sales.model.money;

public interface Money extends Comparable<Money> {
	enum Currency {CREDIT}

	Currency DEFAULT_CURRENCY = Currency.CREDIT;

	Money ZERO = Money.valueOf(0, DEFAULT_CURRENCY);

	static Money valueOf(Rational value, Currency currency) {
		return new RationalMoney(value, currency);
	}

	static Money valueOf(long value, Currency currency) {
		return new RationalMoney(Rational.valueOf(value), currency);
	}

	static Money valueOf(long value) {
		return new RationalMoney(Rational.valueOf(value), DEFAULT_CURRENCY);
	}

	static Money valueOf(float value) { return new RationalMoney(Rational.valueOf((long) (value * 100)), DEFAULT_CURRENCY); }

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
