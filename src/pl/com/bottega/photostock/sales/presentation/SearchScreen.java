package pl.com.bottega.photostock.sales.presentation;

import pl.com.bottega.photostock.sales.application.ProductCatalog;
import pl.com.bottega.photostock.sales.model.Client;
import pl.com.bottega.photostock.sales.model.Product;
import pl.com.bottega.photostock.sales.model.money.Money;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class SearchScreen {
	private final Scanner scanner;
	private final ProductCatalog productCatalog;
	private LoginScreen loginScreen;

	public SearchScreen(Scanner scanner, ProductCatalog productCatalog, LoginScreen loginScreen) {
		this.scanner = scanner;
		this.productCatalog = productCatalog;
		this.loginScreen = loginScreen;
	}

	public void print() {
		String name = getQuery();
		String[] tags = getTags();
		Money priceFrom = getMoney("Cena od");
		Money priceTo = getMoney("Cena do");

		List<Product> products = productCatalog.find(
				loginScreen.getClient(),
				name,
				tags,
				priceFrom,
				priceTo);

//		for (Product product : products)
//			System.out.println(String.format("%s | %s", product.getName(), product.calculatePrice(loginScreen.getClient())));

		printProducts(loginScreen.getClient(), products);
		System.out.println();
	}

	private String getQuery() {
		System.out.print("Nazwa: ");
		return scanner.nextLine();
	}

	private String[] getTags() {
		System.out.print("Tagi: ");
		String input = scanner.nextLine().trim();
		if (input.length() == 0)
			return null;
		return input.split(" ");
	}

	private Money getMoney(String prompt) {
		while (true) {
			try {
				System.out.print(prompt + ": ");
				float f = scanner.nextFloat();
				scanner.nextLine();
				return Money.valueOf(f);
			} catch (InputMismatchException ex) {
				scanner.nextLine();
				System.out.println("Wprowadź poprawną cenę");
			}
		}
	}

	private void printProducts(Client client, List<Product> products) {
		System.out.println("Matching products: ");
		for (Product product : products) {
			System.out.println(
					String.format(
							"%s | %s | %s",
							product.getNumber(),
							product.getName(),
							product.calculatePrice(client)
					)
			);
		}
	}
}