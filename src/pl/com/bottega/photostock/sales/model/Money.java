package pl.com.bottega.photostock.sales.model;

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

	Money opposite();

	Money add(Money addend);

	Money subtract(Money subtrahend);

	Money multiply(long factor);

	RationalMoney convertToRational();
}
