package pl.com.bottega.photostock.sales.presentation;

import pl.com.bottega.photostock.sales.application.AuthenticationProcess;
import pl.com.bottega.photostock.sales.application.LightBoxManagement;
import pl.com.bottega.photostock.sales.application.ProductCatalog;
import pl.com.bottega.photostock.sales.application.PurchaseProcess;
import pl.com.bottega.photostock.sales.infrastructure.InMemoryClientRepository;
import pl.com.bottega.photostock.sales.infrastructure.InMemoryLightBoxRepository;
import pl.com.bottega.photostock.sales.infrastructure.InMemoryProductRepository;
import pl.com.bottega.photostock.sales.infrastructure.InMemoryReservationRepository;
import pl.com.bottega.photostock.sales.model.*;

import java.util.Scanner;

public class PhotoStockMain {

	private LightBoxScreen lightBoxScreen;
	private MainScreen mainScreen;
	private SearchScreen searchScreen;
	private ReservationScreen reservationScreen;
	private OfferScreen offerScreen;
	private LoginScreen loginScreen;

	public PhotoStockMain() {
		Scanner scanner = new Scanner(System.in);
		ClientRepository clientRepository = new InMemoryClientRepository();
		LightBoxRepository lightBoxRepository = new InMemoryLightBoxRepository();
		ReservationRepository reservationRepository = new InMemoryReservationRepository();
		ProductRepository productRepository = new InMemoryProductRepository();
		AuthenticationProcess authenticationProcess = new AuthenticationProcess(clientRepository);
		ProductCatalog productCatalog = new ProductCatalog(new InMemoryProductRepository());
		LightBoxManagement lightBoxManagement =
				new LightBoxManagement(lightBoxRepository, clientRepository, productRepository);
		PurchaseProcess purchaseProcess =
				new PurchaseProcess(clientRepository, reservationRepository, productRepository);
		loginScreen = new LoginScreen(scanner, authenticationProcess);
		searchScreen = new SearchScreen(scanner, productCatalog, loginScreen);
		reservationScreen = new ReservationScreen(scanner, purchaseProcess, loginScreen);
		offerScreen = new OfferScreen(scanner, purchaseProcess, loginScreen);
		lightBoxScreen = new LightBoxScreen(scanner, loginScreen, lightBoxManagement);
		mainScreen = new MainScreen(scanner, searchScreen, reservationScreen, offerScreen, lightBoxScreen);
	}

	public static void main(String[] args) {
		new PhotoStockMain().start();
	}

	private void start() {
		loginScreen.print();
		mainScreen.print();
	}
}
