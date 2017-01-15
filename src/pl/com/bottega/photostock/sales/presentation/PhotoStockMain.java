package pl.com.bottega.photostock.sales.presentation;

import pl.com.bottega.photostock.sales.application.AuthenticationProcess;
import pl.com.bottega.photostock.sales.application.LightBoxManagement;
import pl.com.bottega.photostock.sales.application.ProductCatalog;
import pl.com.bottega.photostock.sales.application.PurchaseProcess;
import pl.com.bottega.photostock.sales.infrastructure.csv.CSVClientRepository;
import pl.com.bottega.photostock.sales.infrastructure.memory.InMemoryLightBoxRepository;
import pl.com.bottega.photostock.sales.infrastructure.memory.InMemoryProductRepository;
import pl.com.bottega.photostock.sales.infrastructure.memory.InMemoryPurchaseRepository;
import pl.com.bottega.photostock.sales.infrastructure.memory.InMemoryReservationRepository;
import pl.com.bottega.photostock.sales.model.client.ClientRepository;
import pl.com.bottega.photostock.sales.model.lightbox.LightBoxRepository;
import pl.com.bottega.photostock.sales.model.product.ProductRepository;
import pl.com.bottega.photostock.sales.model.purchase.PurchaseRepository;
import pl.com.bottega.photostock.sales.model.purchase.ReservationRepository;

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
		ClientRepository clientRepository = new CSVClientRepository("/Users/kprzygudzki/IdeaProjects/Bottega/photostock/resources/");
		LightBoxRepository lightBoxRepository = new InMemoryLightBoxRepository();
		ReservationRepository reservationRepository = new InMemoryReservationRepository();
		ProductRepository productRepository = new InMemoryProductRepository();
		PurchaseRepository purchaseRepository = new InMemoryPurchaseRepository();
		AuthenticationProcess authenticationProcess = new AuthenticationProcess(clientRepository);
		ProductCatalog productCatalog = new ProductCatalog(new InMemoryProductRepository());
		PurchaseProcess purchaseProcess = new PurchaseProcess(clientRepository, reservationRepository,
				productRepository, purchaseRepository);
		LightBoxManagement lightBoxManagement = new LightBoxManagement(purchaseProcess, lightBoxRepository, clientRepository,
				productRepository);
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
