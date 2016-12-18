package pl.com.bottega.photostock.sales.application;

import pl.com.bottega.photostock.sales.model.*;

import java.util.Arrays;
import java.util.Collection;

public class InMemoryLightBoxRepositoryTest {
	public static void main(String[] args) {

		Client client1 = new Client("Zenek", new Address(), Money.valueOf(100));
		Client client2 = new Client("Zdzisiek", new Address(), Money.valueOf(100));

		LightBoxRepository repository = new InMemoryLightBoxRepository();

		ProductRepository productRepository = new InMemoryProductRepository();
		Product product1 = productRepository.get("1");
		Product product2 = productRepository.get("2");
		Product product3 = productRepository.get("3");

		Collection<String> tags = Arrays.asList("samochody", "motoryzacja");

		LightBox l1 = new LightBox(client1, "mój lajtbox");
		LightBox l2 = new LightBox(client2, "mojszy lajtbox");
		LightBox l3 = new LightBox(client1, "najmojszy lajtbox");

		l1.add(product1);
		l1.add(product2);
		l1.add(product3);
		l2.add(product1);
		l2.add(product2);
		l3.add(product3);
		l3.add(product1);

		repository.put(l1);
		repository.put(l2);
		repository.put(l3);

		Collection<LightBox> lightBoxes1 = repository.getFor(client1);
		Collection<LightBox> lightBoxes2 = repository.getFor(client2);

		printLightboxes(lightBoxes1);
		printLightboxes(lightBoxes2);
	}

	private static void printLightboxes(Collection<LightBox> lightBoxes) {
		int number = 1;
		for (LightBox lightBox: lightBoxes) {
			System.out.println(String.format("%d. %s - %s", number, lightBox.getName(), lightBox.getOwner().getName()));
			printLightbox(lightBox);
			number++;
		}
	}

	private static void printLightbox(LightBox lightBox) {
		for (Product product : lightBox) {
			System.out.println(
					String.format("%s%s | %s",
							product.isActive() ? "" : "X ",
							product.getNumber(),
							product.calculatePrice(lightBox.getOwner())
					));
		}
	}
}
