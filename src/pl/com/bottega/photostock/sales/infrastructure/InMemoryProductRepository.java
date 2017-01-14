package pl.com.bottega.photostock.sales.infrastructure;

import pl.com.bottega.photostock.sales.model.client.Client;
import pl.com.bottega.photostock.sales.model.money.Money;
import pl.com.bottega.photostock.sales.model.product.Clip;
import pl.com.bottega.photostock.sales.model.product.Picture;
import pl.com.bottega.photostock.sales.model.product.Product;
import pl.com.bottega.photostock.sales.model.product.ProductRepository;

import java.util.*;

public class InMemoryProductRepository implements ProductRepository {

	private static final Map<String, Product> REPOSITORY = new HashMap<>();

	static {
		Collection<String> tags = Arrays.asList("przyroda", "motoryzacja");
		Product picture1 = new Picture("1", "BMW", tags, Money.valueOf(300));
		Product picture2 = new Picture("2", "Mercedes", tags, Money.valueOf(200));
		Product picture3 = new Picture("3", "Porsche", tags, Money.valueOf(400));
		Product clip1 = new Clip("4", "Wściekłe pięści węża", 2L * 60 * 1000, Money.valueOf(500));
		Product clip2 = new Clip("5", "Sarnie żniwo", 40L * 60 * 1000, Money.valueOf(1000));
		REPOSITORY.put(picture1.getNumber(), picture1);
		REPOSITORY.put(picture2.getNumber(), picture2);
		REPOSITORY.put(picture3.getNumber(), picture3);
		REPOSITORY.put(clip1.getNumber(), clip1);
		REPOSITORY.put(clip2.getNumber(), clip2);
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

	@Override
	public List<Product> find(String nameQuery, String[] tags, Money priceFrom, Money priceTo, boolean onlyActive,
							  Client client
	) {
		List<Product> matchingProducts = new LinkedList<>();
		for(Product product : REPOSITORY.values()){
			if (matches(product, nameQuery, tags, priceFrom, priceTo, onlyActive, client))
				matchingProducts.add(product);
		}
		return matchingProducts;
	}

	private boolean matches(Product product, String nameQuery, String[] tags, Money priceFrom, Money priceTo,
							boolean onlyAvailable, Client client) {
		return matchesQuery(product, nameQuery) &&
				matchesTags(product, tags) &&
				matchesPriceFrom(product, priceFrom, client) &&
				matchesPriceTo(product, priceTo, client) &&
				matchesOnlyAvailable(product, onlyAvailable);
	}

	private boolean matchesOnlyAvailable(Product product, boolean onlyAvailable) {
		return !onlyAvailable || product.isAvailable();
	}

	private boolean matchesQuery(Product product, String nameQuery) {
		return nameQuery == null ||
				product.getName().toLowerCase().startsWith(nameQuery.toLowerCase());
	}

	private boolean matchesTags(Product product, String[] tags) {
		if (tags == null || tags.length == 0)
			return true;
		if (!(product instanceof Picture))
			return false;
		Picture picture = (Picture) product;
		for (String tag : tags)
			if (!picture.hasTag(tag))
				return false;
		return true;
	}

	private boolean matchesPriceFrom(Product product, Money priceFrom, Client client) {
		return priceFrom == null ||
				product.calculatePrice(client).gte(priceFrom);
	}

	private boolean matchesPriceTo(Product product, Money priceTo, Client client) {
		return priceTo == null ||
				product.calculatePrice(client).lte(priceTo);
	}
}
