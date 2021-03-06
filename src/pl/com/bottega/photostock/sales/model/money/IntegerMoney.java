package pl.com.bottega.photostock.sales.model.money;

public class IntegerMoney implements Money {

	private long cents;
	private Currency currency;

	IntegerMoney(long cents, Currency currency) {
		this.cents = cents;
		this.currency = currency;
	}

	@Override
	public Money opposite() {
		return new IntegerMoney(-cents, this.currency);
	}

	@Override
	public Money add(Money addend) {
		IntegerMoney integerAddend = safeConvert(addend);
		return new IntegerMoney(cents + integerAddend.cents, this.currency);
	}

	@Override
	public Money subtract(Money subtrahend) {
		IntegerMoney integerSubtrahend = safeConvert(subtrahend);
		return new IntegerMoney(cents - integerSubtrahend.cents, this.currency);
	}

	@Override
	public Money multiply(long factor) {
		return new IntegerMoney(cents * factor, this.currency);
	}

	private IntegerMoney safeConvert(Money other) {
		IntegerMoney integerMoney = other.convertToInteger();
		ensureSameCurrency(integerMoney);

		return integerMoney;
	}

	private void ensureSameCurrency(IntegerMoney other) {
		if (this.currency != other.currency)
			throw new IllegalArgumentException("Currency missmatch");
	}

	@Override
	public RationalMoney convertToRational() {
		return new RationalMoney(Rational.valueOf(cents, 100), currency);
	}

	@Override
	public IntegerMoney convertToInteger() {
		return this;
	}

	@Override
	public int compareTo(Money money) {
		IntegerMoney integerMoney = safeConvert(money);

		if (cents == integerMoney.cents)
			return 0;
		else if (cents > integerMoney.cents)
			return 1;
		else
			return -1;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) return true;
		if ((other == null) || !(other instanceof Money)) return false;
		IntegerMoney o;
		if (other instanceof RationalMoney) {
			RationalMoney otherAsRational = (RationalMoney) other;
			o = otherAsRational.convertToInteger();
		} else {
			o = (IntegerMoney) other;
		}
		return this.currency == o.currency && this.cents == o.cents;
	}

	@Override
	public int hashCode() {
		Long l = cents;
		return (int) (l ^ (l >>> 32));
	}

	@Override
	public String toString() {
		return cents / 100 + "." + cents % 100 + " " + currency;
	}

}
