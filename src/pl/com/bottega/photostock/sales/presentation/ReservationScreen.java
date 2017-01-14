package pl.com.bottega.photostock.sales.presentation;

import pl.com.bottega.photostock.sales.application.PurchaseProcess;
import pl.com.bottega.photostock.sales.model.product.ProductNotAvailableException;

import java.util.Scanner;

public class ReservationScreen {
	private Scanner scanner;
	private PurchaseProcess purchaseProcess;
	private LoginScreen loginScreen;

	public ReservationScreen(Scanner scanner, PurchaseProcess purchaseProcess, LoginScreen loginScreen) {
		this.scanner = scanner;
		this.purchaseProcess = purchaseProcess;
		this.loginScreen = loginScreen;
	}

	public void print() {
		while (true) {
			System.out.print("Podaj numer produktu do rezerwacji: ");
			String productNumber = scanner.nextLine();
			try {
				String clientNumber = loginScreen.getAuthenticatedClientNumber();
				String reservationNumber = purchaseProcess.getReservation(clientNumber);
				purchaseProcess.add(reservationNumber, productNumber);
				System.out.println(String.format(
						"Produkt %s został dodany do rezerwacji %s",
						productNumber, reservationNumber
						)
				);
			} catch (ProductNotAvailableException ex) {
				System.out.println(String.format("Przepraszamy, produkt %s jest niedostępny", productNumber));
			} catch (IllegalArgumentException ex) {
				System.out.println("Nieprawidłowy numer produktu");
			}
			return;
		}
	}
}
