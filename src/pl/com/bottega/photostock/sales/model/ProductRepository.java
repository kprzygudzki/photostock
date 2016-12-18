package pl.com.bottega.photostock.sales.model;

public interface ProductRepository {
	Product get(String name);
	void put(Product product);
}
