package pl.com.bottega.photostock.sales.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class InMemoryProductRepository implements ProductRepository {

	private static final Map<String, Product> REPOSITORY = new HashMap<>();

	static {
		Collection<String> tags = Arrays.asList("przyroda", "motoryzacja");
		Product picture1 = new Picture("1", "BMW", tags, Money.valueOf(3));
		Product picture2 = new Picture("2", "Mercedes", tags, Money.valueOf(2));
		Product picture3 = new Picture("3", "Porsche", tags, Money.valueOf(4));
		Product clip1 = new Clip("4", "Wściekłe pięści węża", 2l * 60 * 1000, Money.valueOf(5));
		Product clip2 = new Clip("5", "Sarnie żniwo", 40l * 60 * 1000, Money.valueOf(10));
		REPOSITORY.put(picture1.getNumber(), picture1);
		REPOSITORY.put(picture2.getNumber(), picture2);
		REPOSITORY.put(picture3.getNumber(), picture3);
		REPOSITORY.put(clip1.getNumber(), picture3);
		REPOSITORY.put(clip2.getNumber(), picture3);
	}

	@Override
	public void put(Product product) {
		String number = product.getNumber();
		REPOSITORY.putIfAbsent(number, product);
	}

	@Override
	public Product get(String number) {
		return REPOSITORY.get(number);
	}
}
