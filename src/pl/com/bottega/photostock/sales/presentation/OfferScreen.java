package pl.com.bottega.photostock.sales.presentation;

import pl.com.bottega.photostock.sales.application.PurchaseProcess;
import pl.com.bottega.photostock.sales.model.Offer;
import pl.com.bottega.photostock.sales.model.Product;

import java.util.Scanner;

public class OfferScreen {
	private Scanner scanner;
	private PurchaseProcess purchaseProcess;
	private LoginScreen loginScreen;

	public OfferScreen(Scanner scanner, PurchaseProcess purchaseProcess, LoginScreen loginScreen) {
		this.scanner = scanner;
		this.purchaseProcess = purchaseProcess;
		this.loginScreen = loginScreen;
	}

	public void print() {
		String reservationNumber = purchaseProcess.getReservation(loginScreen.getAuthenticatedClientNumber());
		try {
			Offer offer = purchaseProcess.calculateOffer(reservationNumber);
			printOffer(offer);
		} catch (IllegalStateException ex) {
			System.out.println("Nie ma aktywnych produktów w rezerwacji. Dodaj produkty.");
		}
		System.out.println("Czy chcesz dokonać zakupu?");
		getConfirmation();

	}

	private void getConfirmation() {
		scanner.nextLine();
		confirm();
	}

	private void printOffer(Offer offer) {
		System.out.println("Oferta specjalnie dla Ciebie:");
		int i = 1;
		for(Product product : offer.getItems()) {
			System.out.println(String.format("%d | %s", i++, product.getName()));
		}
		System.out.println(String.format("Zaledwie: %s", offer.getTotalCost()));
	}

	private void confirm() {
	}
}
