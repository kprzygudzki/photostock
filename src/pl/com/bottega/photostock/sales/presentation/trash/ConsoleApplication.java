package pl.com.bottega.photostock.sales.presentation.trash;

import pl.com.bottega.photostock.sales.infrastructure.memory.InMemoryLightBoxRepository;
import pl.com.bottega.photostock.sales.infrastructure.memory.InMemoryProductRepository;
import pl.com.bottega.photostock.sales.model.client.Address;
import pl.com.bottega.photostock.sales.model.client.Client;
import pl.com.bottega.photostock.sales.model.client.VIPClient;
import pl.com.bottega.photostock.sales.model.lightbox.LightBoxRepository;
import pl.com.bottega.photostock.sales.model.money.Money;
import pl.com.bottega.photostock.sales.model.money.RationalMoney;
import pl.com.bottega.photostock.sales.model.product.Product;
import pl.com.bottega.photostock.sales.model.product.ProductRepository;
import pl.com.bottega.photostock.sales.model.purchase.Offer;
import pl.com.bottega.photostock.sales.model.purchase.Purchase;
import pl.com.bottega.photostock.sales.model.purchase.Reservation;

public class ConsoleApplication {
	public static void main(String[] args) {

		//preparing the scenario
		LightBoxRepository repository = new InMemoryLightBoxRepository();

		ProductRepository productRepository = new InMemoryProductRepository();
		Product product1 = productRepository.get("1");
		Product product2 = productRepository.get("2");
		Product product3 = productRepository.get("3");

		Client client1 = new Client("Johnny X", new Address(), Money.valueOf(100));
		System.out.println(client1.introduce());
		Client client2 = new VIPClient("Mr. Marcellus Wallace", new Address(), RationalMoney.ZERO, Money.valueOf(100));
		System.out.println(client2.introduce());
		Reservation reservation1 = new Reservation(client2);

		//client behavior scenario
		reservation1.add(product1);
		reservation1.add(product2);
		reservation1.add(product3);
		System.out.println("After adding items count: " + reservation1.getItemsCount());

		Offer offer1 = reservation1.generateOffer();

		client2.canAfford(offer1.getTotalCost());

		boolean canAfford = client2.canAfford(offer1.getTotalCost());
		System.out.println("Client can afford: " + canAfford);

		if (canAfford) {
			client1.charge(offer1.getTotalCost(), "Test purchase");
			Purchase purchase1 = new Purchase(client2, product1, product2, product3);
			System.out.println();
		} else {
			System.out.println("Client cannot afford");
		}
	}
}
