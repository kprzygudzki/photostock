package pl.com.bottega.photostock.sales.presentation;

import pl.com.bottega.photostock.sales.application.AuthenticationProcess;
import pl.com.bottega.photostock.sales.model.Client;

import java.util.Scanner;

public class LoginScreen {
	private AuthenticationProcess authenticationProcess;
	private Scanner scanner;
	private Client client;

	public LoginScreen(Scanner scanner, AuthenticationProcess authenticationProcess) {
		this.authenticationProcess = authenticationProcess;
		this.scanner = scanner;
	}

	public void print() {
		while (true) {
			System.out.print("Podaj tajemny numer klienta: ");
			String clientNumber = scanner.nextLine();
			client = authenticationProcess.authenticate(clientNumber);
			if (client != null) {
				System.out.println(String.format("Witaj, %s!", client.getName()));
				return;
			}
			System.out.println("Nieprawidłowy sekretny numer klienta. Spróbuj ponownie.");
		}
	}

	public String getAuthenticatedClientNumber() {
		return client.getNumber();
	}

	public Client getClient() {
		return client;
	}
}
