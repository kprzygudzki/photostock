package pl.com.bottega.photostock.sales.application;

import pl.com.bottega.photostock.sales.model.*;

import java.util.HashSet;

public class ProductEqualsTest {

	public static void main(String[] args) {
		Product product1 = picture("123");
		Product product2 = picture(null);
		Product product3 = picture("123");
		Product product4 = picture("066");
		Product product5 = picture(null);

		System.out.println("Positive:");
		System.out.println(product1.equals(product3));
		System.out.println(product3.equals(product1));
		System.out.println(product1.equals(product1));
		System.out.println(product2.equals(product2));

		System.out.println("Negative:");
		System.out.println(product1.equals(product2));
		System.out.println(product3.equals(product4));
		System.out.println(product2.equals(product4));
		System.out.println(product1.equals(null));
		System.out.println(product1.equals(13));
	}

	private static Product picture(String number) {
		return new Picture(number, "Name", new HashSet<>(), Money.valueOf(100));
	}

	private static Product clip(String number) {
		return new Clip(number, "", 500l, Money.valueOf(100));
	}
}
