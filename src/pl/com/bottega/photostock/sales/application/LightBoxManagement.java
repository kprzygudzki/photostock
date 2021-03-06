package pl.com.bottega.photostock.sales.application;

import pl.com.bottega.photostock.sales.model.client.Client;
import pl.com.bottega.photostock.sales.model.client.ClientRepository;
import pl.com.bottega.photostock.sales.model.lightbox.LightBox;
import pl.com.bottega.photostock.sales.model.lightbox.LightBoxRepository;
import pl.com.bottega.photostock.sales.model.product.Product;
import pl.com.bottega.photostock.sales.model.product.ProductRepository;

import java.util.Collection;

public class LightBoxManagement {

	private PurchaseProcess purchaseProcess;
	private LightBoxRepository lightBoxRepository;
	private ClientRepository clientRepository;
	private ProductRepository productRepository;

	public LightBoxManagement(PurchaseProcess purchaseProcess, LightBoxRepository lightBoxRepository,
							  ClientRepository clientRepository, ProductRepository productRepository) {
		this.purchaseProcess = purchaseProcess;
		this.lightBoxRepository = lightBoxRepository;
		this.clientRepository = clientRepository;
		this.productRepository = productRepository;
	}

	public Collection<String> getLightBoxNames(String clientNumber) {
		Client client = getClient(clientNumber);
		return lightBoxRepository.getNamesFor(client);
	}

	private Client getClient(String clientNumber) {
		Client client = clientRepository.get(clientNumber);
		if (client == null)
			throw new IllegalArgumentException(String.format("Klient %s doesn't exist", clientNumber));
		return client;
	}

	public LightBox getLightBox(String clientNumber, String lightBoxName) {
		Client client = getClient(clientNumber);
		LightBox lightBox = lightBoxRepository.findLightBox(client, lightBoxName);
		if (lightBox == null)
			throw new IllegalArgumentException(String.format("LightBox %s doesn't exist", lightBoxName));
		return lightBox;
	}

	private Product getProduct(String productNumber) {
		Product product = productRepository.get(productNumber);
		if (product == null)
			throw new IllegalArgumentException(String.format("Product %s doesn't exist", productNumber));
		return product;
	}

	public void addProduct(String clientNumber, String lightBoxName, String productNumber) {
		Client client = getClient(clientNumber);
		LightBox lightBox = getOrCreateLightBox(lightBoxName, client);
		Product product = getProduct(productNumber);
		lightBox.add(product);
	}

	private LightBox getOrCreateLightBox(String lightBoxName, Client client) {
		LightBox lightBox = lightBoxRepository.findLightBox(client, lightBoxName);
		if (lightBox == null) {
			lightBox = new LightBox(client, lightBoxName);
			lightBoxRepository.put(lightBox);
		}
		return lightBox;
	}

	public void makeReservation(String clientNumber, String lightBoxName) {
		LightBox lightBox = getLightBox(clientNumber, lightBoxName);
		String reservationNumber = purchaseProcess.getReservation(clientNumber);
		for (Product product : lightBox) {
			if (product.isAvailable())
				purchaseProcess.add(reservationNumber, product.getNumber());
		}
	}
}
