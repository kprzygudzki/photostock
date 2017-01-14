package pl.com.bottega.photostock.sales.model;

import pl.com.bottega.photostock.sales.model.money.Money;

import java.util.List;

public interface ProductRepository {

	Product get(String name);

	void put(Product product);

	List<Product> find(String nameQuery,
					   String[] tags,
					   Money priceFrom,
					   Money priceTo,
					   boolean onlyActive,
					   Client client
	);
}
