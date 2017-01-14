package pl.com.bottega.photostock.sales.presentation;

import pl.com.bottega.photostock.sales.application.LightBoxManagement;
import pl.com.bottega.photostock.sales.model.lightbox.LightBox;
import pl.com.bottega.photostock.sales.model.product.Product;
import pl.com.bottega.photostock.sales.model.product.ProductNotAvailableException;

import java.util.Collection;
import java.util.Scanner;

public class LightBoxScreen {

	private Scanner scanner;
	private LoginScreen loginScreen;
	private LightBoxManagement lightBoxManagement;
	private boolean exit;

	public LightBoxScreen(Scanner scanner, LoginScreen loginScreen, LightBoxManagement lightBoxManagement) {
		this.scanner = scanner;
		this.loginScreen = loginScreen;
		this.lightBoxManagement = lightBoxManagement;
	}

	public void print() {
		String command;
		exit = false;
		while (!exit) {
			printMenu();
			command = getCommand();
			execute(command);
		}
	}

	private String getCommand() {
		return scanner.nextLine();
	}

	private void printMenu() {
		System.out.println("Użyj komendy aby:\n" +
				"pokaz -> wyswietlić nazwy wszystkich dostępnych lighboxow\n" +
				"pokaz [nazwa lightboxa] -> wyswietlić produkty znajdujace się w lighboxie o zadanej nazwie\n" +
				"dodaj [nazwa lightboxa] [nr produktu] -> dodać do lightboxa o zadanej nazwie produkt o zadanym numerze\n" +
				"rezerwuj [nazwa lightboxa] -> dodać elementy lightboxa do rezerwacji\n" +
				"powrot -> wrócić do menu głównego aplikacji\n"
		);
	}

	private void execute(String command) {
		String args[] = parseCommand(command);
		switch (args[0]) {
			case "pokaz":
				show(args);
				break;
			case "dodaj":
				add(args);
				break;
			case "rezerwuj":
				reserveLightBox(args);
				break;
			case "powrot":
				exit = true;
				break;
			default:
				invalidCommandMessage();
		}
	}

	private void reserveLightBox(String[] args) {
		if (args.length != 2) {
			invalidCommandMessage();
			return;
		}
		String clientNumber = getClientNumber();
		String lightBoxName = args[1];
		lightBoxManagement.makeReservation(clientNumber, lightBoxName);
		System.out.println("Produkty zostały dodane do rezerwacji");
	}

	private void show(String[] args) {
		String clientNumber = getClientNumber();
		int numberOfArguments = args.length - 1;
		switch (numberOfArguments) {
			case 0:
				printLightBoxes(clientNumber);
				break;
			case 1:
				String lightBoxName = args[1];
				printLightBox(clientNumber, lightBoxName);
				break;
			default:
				invalidCommandMessage();
		}
	}

	private String getClientNumber() {
		return loginScreen.getAuthenticatedClientNumber();
	}

	private void printLightBox(String clientNumber, String lightBoxName) {
		try {
			LightBox lightBox = lightBoxManagement.getLightBox(clientNumber, lightBoxName);
			System.out.println(String.format("Produkty w lightBoxie %s:", lightBoxName));
			int i = 1;
			for (Product product : lightBox)
				System.out.println(
						String.format("%d. %s | %s | %s%s",
								i++,
								product.getNumber(),
								product.getName(),
								product.calculatePrice(lightBox.getOwner()),
								product.isActive() ? "" : " (inactive)"
						)
				);
		} catch (IllegalArgumentException ex) {
			System.out.println(String.format("LightBox %s nie istnieje.", lightBoxName));
		}
	}

	private void printLightBoxes(String clientNumber) {
		Collection<String> lightBoxNames = lightBoxManagement.getLightBoxNames(clientNumber);
		if (lightBoxNames.isEmpty())
			System.out.println("Nie masz żadnych lightBoxów. Dodaj nowy lightBox.");
		else {
			System.out.println("Dostępne lightBoxy:");
			int i = 1;
			for (String lightBoxName : lightBoxNames)
				System.out.println(String.format("%d. %s", i++, lightBoxName));
		}
	}

	private void add(String[] args) {
		if (args.length != 3) {
			invalidCommandMessage();
			return;
		}
		String lightBoxName = args[1];
		String productNumber = args[2];

		String clientNumber = getClientNumber();
		try {
			lightBoxManagement.addProduct(clientNumber, lightBoxName, productNumber);
			System.out.println(String.format("Produkt %s został dodany do lightBoxa %s",
					productNumber, lightBoxName));
		} catch (ProductNotAvailableException ex) {
			System.out.println(String.format("Produkt %s nie jest dostępny", productNumber));
		} catch (IllegalArgumentException ex) {
			System.out.println(String.format("Produkt %s nie może zostać dodany do lightBoxa %s",
					productNumber, lightBoxName));
		}
	}

	private String[] parseCommand(String command) {
		return command.split(" ");
	}

	private void invalidCommandMessage() {
		System.out.println("Sorry, nie rozumiem. :(");
	}
}
